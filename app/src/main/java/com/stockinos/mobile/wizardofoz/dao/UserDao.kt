package com.stockinos.mobile.wizardofoz.dao

import android.util.Log
import com.stockinos.mobile.wizardofoz.models.User
import com.stockinos.mobile.wizardofoz.models.UserWithMessages
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesAboutUser
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class UserDao(private val userDao: IUserDao) {
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
                .filterKeys { it.type == "customer" }
                .map { (user, messages) ->
                    Log.d(TAG, "Map. User: $user. Messages: $messages")
                    MessagesAboutUser(
                        user,
                        messages.sortedBy { m -> m.iTimestamp },
                        messages.count { m -> m.state == "unread" }
                    )
                }
                .sortedBy { m -> m.lastMessage.iTimestamp }
        }
    }
}
