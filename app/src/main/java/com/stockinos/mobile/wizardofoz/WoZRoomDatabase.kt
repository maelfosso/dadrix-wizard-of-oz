package com.stockinos.mobile.wizardofoz

import android.content.Context
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import com.stockinos.mobile.wizardofoz.dao.IUserDao
import com.stockinos.mobile.wizardofoz.dao.IMessageDao
import com.stockinos.mobile.wizardofoz.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        User::class,
        Message::class,
        MessageText::class,
        MessageImage::class,
        MessageAudio::class
   ],
    version = 12,
    exportSchema = true
)
public abstract class WoZRoomDatabase: RoomDatabase() {
    abstract fun messageDao(): IMessageDao
    abstract fun userDao(): IUserDao

    private class WozDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // db.delete("whatsapp_messages")
            // db.execSQL("DROP TABLE whatsapp_messages")

            INSTANCE?.let { database ->
                scope.launch {
                    // database.
                    populateDatabase(database.messageDao())
                }
            }
        }

        fun populateDatabase(messageDao: IMessageDao) {
            // Delete all content here
            // messageDao.getMessagesAboutUser()

            // Add some sample message
            // var msg = Message()
            // messageDao.insert(msg)
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: WoZRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WoZRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WoZRoomDatabase::class.java,
                    "woz_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WozDatabaseCallback(scope))
                    .build()
                INSTANCE = instance

                // return instance
                instance
            }
        }
    }
}
