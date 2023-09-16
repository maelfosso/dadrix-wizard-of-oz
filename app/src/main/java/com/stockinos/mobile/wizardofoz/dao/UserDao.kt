package com.stockinos.mobile.wizardofoz.dao

import android.util.Log
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import com.stockinos.mobile.wizardofoz.dto.MessageDTO
import com.stockinos.mobile.wizardofoz.dto.MessageTextDTO
import com.stockinos.mobile.wizardofoz.models.*
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesAboutUser
import kotlinx.coroutines.flow.*

class UserDao(
    private val userDao: IUserDao,
    private val messageDao: IMessageDao
) {
    companion object {
        private val TAG = UserDao::class.java.name
    }

    fun insert(user: User): Long {
        return userDao.insert(user)
    }

    fun getMessagesAboutUser(): Flow<List<MessagesAboutUser>> {
        val sentMessages = userDao.getUserWithSentMessages().map { it -> it.map { UserWithMessages(it.user, it.messages) } }
        val receivedMessages = userDao.getUserWithReceivedMessages().map { it -> it.map { UserWithMessages(it.user, it.messages) } }

        return sentMessages.zip(receivedMessages) { sent, received ->
            (sent + received)
                .groupBy ({ it.user }, { it.messages })
                .mapValues { (_, values) -> values.flatten() }
                .filterKeys { it.type == USER_TYPE_CUSTOMER }
                .mapValues {
                    val messages = it.value
                    var messagesDTO = mutableListOf<MessageDTO>()

                    for (message in messages) {
                        when (message.type) {
                            MESSAGE_TYPE_TEXT -> {
                                val messageText = messageDao.getMessageText(message.id)
                                Log.d("UserDAO", "Message TEXT: ${messageText}")
                                Log.d("UserDAO", "Message: ${message.id} - ${message.from} - ${message.to} - ${message.timestamp} - ${message.type}")
                                messagesDTO.add(
                                    MessageTextDTO(
                                        id = message.id,
                                        from = message.from,
                                        to = message.to,
                                        timestamp = message.timestamp,
                                        type = message.type,

                                        textId = messageText.textId,
                                        body = messageText.body
                                    )
                                )
                            }
                            else -> throw NotImplementedError()
                        }
                    }

                    messagesDTO.toList()
                }
                .map { (user, messages) ->
                    Log.d(TAG, "Map. User: $user. Messages: $messages")
                    MessagesAboutUser(
                        user,
                        messages.sortedBy { m -> m.timestamp.toLong() },
                        messages.count { m -> m.state == "unread" }
                    )
                }
                .sortedBy { m -> m.lastMessage.timestamp.toLong() }
        }
    }
}
