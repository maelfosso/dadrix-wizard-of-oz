package com.stockinos.mobile.wizardofoz.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesByUser
import kotlinx.coroutines.flow.Flow

@Dao
interface IWhatsappMessageDao {
    @Query("SELECT * FROM whatsapp_messages")
    fun getWhatsappMessages(): Flow<List<WhatsappMessage>>

    @Query("SELECT * FROM whatsapp_messages WHERE `from` = :user OR `to` = :user ORDER BY timestamp DESC")
    fun getWhatsappMessagesAboutUser(user: String): Flow<List<WhatsappMessage>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(message: WhatsappMessage): Long
}
