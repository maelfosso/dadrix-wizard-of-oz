package com.stockinos.mobile.wizardofoz.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.stockinos.mobile.wizardofoz.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE `phone_number` = :phoneNumber")
    fun getUserByPhoneNumber(phoneNumber: String): Flow<User>

    @Transaction
    @Query("SELECT * FROM users")
    fun getUserWithSentMessages(): Flow<List<UserWithSentMessages>>

    @RawQuery
    fun getUserWithAllSentMessages(query: SupportSQLiteQuery): Flow<List<UserWithSentMessages>>

    @Transaction
    @Query("SELECT * FROM users")
    fun getUserWithReceivedMessages(): Flow<List<UserWithReceivedMessages>>
}
