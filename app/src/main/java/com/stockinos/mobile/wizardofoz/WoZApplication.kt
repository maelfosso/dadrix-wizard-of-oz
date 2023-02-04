package com.stockinos.mobile.wizardofoz

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.repositories.WhatsappMessageRepository
import com.stockinos.mobile.wizardofoz.ui.conversation.ConversationUiState
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import java.net.URISyntaxException

class WoZApplication: Application() {
    companion object {
        private val TAG = WoZApplication::class.simpleName
    }

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're need
    // rather than when the application starts
    private val database by lazy { WoZRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WhatsappMessageRepository(database.whatsappMessageDao()) }


    private var _mSocket: Socket? = null
        get() {
            if (field == null) {
                try {
                    val opts = IO.Options()
                    field = IO.socket("http://10.0.2.2:4000", opts)
                    setupSocket()
                } catch (e: URISyntaxException) {
                    Log.d(TAG, "Error connecting socking : ${e.message}")
                }
            }

            return field
        }
    val mSocket: Socket = _mSocket!!

    private var onWhatsappMessageReceived = Emitter.Listener {
        Log.d(TAG, "on whatsapp:message:received before : ${it[0]}")
        val data = Gson().fromJson(it[0].toString(), WhatsappMessage::class.java)
        Log.d(TAG, "on whatsapp:message:received : $data")

        repository.insert(data)
//        Log.d(TAG, "NB Items : ${repository.allWhatsappMessages.count()}")
    }

    fun connectSocket() {
        Log.i(TAG, "connectSocket() - ${_mSocket == null}")

        _mSocket?.on("whatsapp:message:received", onWhatsappMessageReceived)
        _mSocket?.connect()
            ?.on(Socket.EVENT_CONNECT) {
                Log.i(TAG, "connected")

                // sent an authentication message to say that it's an Oscar-ian
            }
            ?.on(Socket.EVENT_DISCONNECT) { Log.i(TAG, "disconnected") }

    }

    fun disconnectSocket() {
        _mSocket?.disconnect()
    }

    fun emitNotice(msg: String) {
        _mSocket?.emit("notice", msg)
    }
    private fun setupSocket() {
        Log.i(TAG, "setupSocket")
        // Setup Listener
    }
}