package com.stockinos.mobile.wizardofoz.dao

import androidx.room.*
import com.stockinos.mobile.wizardofoz.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IMessageDao {
    @Query("SELECT * FROM messages")
    fun getMessages(): Flow<List<Message>>

    @Query("SELECT * FROM messages_texts WHERE id = :textId")
    suspend fun getMessageText(textId: String): MessageText

    @Query("SELECT * FROM messages_images")
    fun getMessageImage(): MessageImage

    @Query("SELECT * FROM messages_audios")
    fun getMessageAudio(): MessageAudio

    @Transaction
    @Query("SELECT * FROM messages WHERE `from` = :phoneNumber OR `to` = :phoneNumber ORDER BY timestamp DESC")
    fun getMessagesAboutUser(phoneNumber: String): Flow<List<MessageWithUser>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(message: Message): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(messageText: MessageText): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(messageImage: MessageImage): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(messageAudio: MessageAudio): Long
}
