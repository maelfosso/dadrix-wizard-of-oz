package com.stockinos.mobile.wizardofoz.dao

import android.util.Log
import androidx.annotation.WorkerThread
import com.stockinos.mobile.wizardofoz.api.models.requests.OnWhatsappMessageReceived
import com.stockinos.mobile.wizardofoz.models.Message
import com.stockinos.mobile.wizardofoz.models.MessageWithUser
import kotlinx.coroutines.flow.Flow

class MessageDao(
    private val messageDao: IMessageDao,
    private val userDao: IUserDao
) {
    companion object {
        private val TAG = MessageDao::class.simpleName
    }
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed
    val allMessages: Flow<List<Message>> = messageDao.getMessages()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main work
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(wappMessage: OnWhatsappMessageReceived) {
        userDao.insert(wappMessage.from)
        userDao.insert(wappMessage.to)

        val message = wappMessage.toMessage()
        val result = messageDao.insert(message)
        Log.d(TAG, "insert : $result")
    }

    fun allMessagesAboutUser(user: String): Flow<List<MessageWithUser>> {
        return messageDao.getMessagesAboutUser(user)
    }

}