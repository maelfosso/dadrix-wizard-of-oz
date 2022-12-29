package com.stockinos.mobile.wizardofoz.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.dao.WhatsappMessageDao
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import kotlinx.coroutines.flow.Flow

class WhatsappMessageRepository(private val whatsappMessageDao: WhatsappMessageDao) {
    companion object {
        private val TAG = WhatsappMessageRepository::class.simpleName
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
}