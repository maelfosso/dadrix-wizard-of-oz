package com.stockinos.mobile.wizardofoz.dao

import android.util.Log
import androidx.annotation.WorkerThread
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import kotlinx.coroutines.flow.Flow

class WhatsappMessageDao(private val whatsappMessageDao: IWhatsappMessageDao) {
    companion object {
        private val TAG = WhatsappMessageDao::class.simpleName
    }
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed
    val allWhatsappMessages: Flow<List<WhatsappMessage>> = whatsappMessageDao.getWhatsappMessages()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main work
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(message: WhatsappMessage) {
        val result = whatsappMessageDao.insert(message)
        Log.d(TAG, "insert : $result")
    }

    fun allWhatsappMessagesAboutUser(user: String): Flow<List<WhatsappMessage>> {
        return whatsappMessageDao.getWhatsappMessagesAboutUser(user)
    }

}