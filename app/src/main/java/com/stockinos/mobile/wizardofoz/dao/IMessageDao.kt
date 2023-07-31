package com.stockinos.mobile.wizardofoz.dao

import androidx.room.*
import com.stockinos.mobile.wizardofoz.models.Message
import com.stockinos.mobile.wizardofoz.models.MessageWithUser
import kotlinx.coroutines.flow.Flow

@Dao
interface IMessageDao {
    @Query("SELECT * FROM messages")
    fun getMessages(): Flow<List<Message>>

    @Transaction
    @Query("SELECT * FROM messages WHERE `from` = :phoneNumber OR `to` = :phoneNumber ORDER BY timestamp DESC")
    fun getMessagesAboutUser(phoneNumber: String): Flow<List<MessageWithUser>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(message: Message): Long
}
