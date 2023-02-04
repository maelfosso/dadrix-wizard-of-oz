package com.stockinos.mobile.wizardofoz

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.stockinos.mobile.wizardofoz.dao.WhatsappMessageDao
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.models.WhatsappMessageAudio
import com.stockinos.mobile.wizardofoz.models.WhatsappMessageImage
import com.stockinos.mobile.wizardofoz.models.WhatsappMessageText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = arrayOf(
        WhatsappMessage::class,
        WhatsappMessageText::class,
        WhatsappMessageImage::class,
        WhatsappMessageAudio::class
    ),
    version = 4,
    exportSchema = true
)
public abstract class WoZRoomDatabase: RoomDatabase() {
    abstract fun whatsappMessageDao(): WhatsappMessageDao


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
                    populateDatabase(database.whatsappMessageDao())
                }
            }
        }

        fun populateDatabase(whatsappMessageDao: WhatsappMessageDao) {
            // Delete all content here
            // whatsappMessageDao.deleteAll()

            // Add some sample message
            // var msg = WhatsappMessage()
            // whatsappMessageDao.insert(msg)
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
