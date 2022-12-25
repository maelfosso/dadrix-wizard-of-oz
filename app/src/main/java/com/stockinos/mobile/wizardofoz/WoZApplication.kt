package com.stockinos.mobile.wizardofoz

import android.app.Application
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URISyntaxException

class WoZApplication: Application() {
    companion object {
        private val TAG = WoZApplication::class.simpleName
    }

    private var mSocket: Socket? = null
        get() {
            if (field == null) {
                try {
                    val opts = IO.Options()
//                    opts.forceNew = true
//                    opts.reconnection = false
//                    field = IO.socket("https://api.stockinos.ngrok.io", opts)
                    field = IO.socket("http://10.0.2.2:4000", opts)
                    setupSocket()
                } catch (e: URISyntaxException) {
                    Log.d(TAG, "Error connecting socking : ${e.message}")
                }
            }

            return field
        }

    private var onWhatsappMessageReceived = Emitter.Listener {
        var data = it[0] as JSONObject
        Log.d(TAG, "on whatsapp:message:received : $data")
    }

    fun connectSocket() {
        Log.i(TAG, "connectSocket() - ${mSocket == null}")

        mSocket?.on("whatsapp:message:received", onWhatsappMessageReceived)
        mSocket?.connect()
            ?.on(Socket.EVENT_CONNECT) {
                Log.i(TAG, "connected")

                // sent an authentication message to say that it's an Oscar-ian
            }
            ?.on(Socket.EVENT_DISCONNECT) { Log.i(TAG, "disconnected") }

    }

    fun disconnectSocket() {
        mSocket?.disconnect()
    }

    fun emitNotice(msg: String) {
        mSocket?.emit("notice", msg)
    }
    private fun setupSocket() {
        Log.i(TAG, "setupSocket")
        // Setup Listener
    }
}