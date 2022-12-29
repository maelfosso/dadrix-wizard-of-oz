package com.stockinos.mobile.wizardofoz.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface WhatsappMessageDao {
    @Query("SELECT * FROM whatsapp_messages")
    fun getWhatsappMessages(): Flow<List<WhatsappMessage>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(message: WhatsappMessage): Long
}