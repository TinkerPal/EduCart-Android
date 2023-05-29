package tech.hackcity.educarts.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.hackcity.educarts.domain.dao.AddressBookDao
import tech.hackcity.educarts.domain.model.AddressBook

@Database(entities = [AddressBook::class], version = 1)

abstract class UserDatabase : RoomDatabase() {
    abstract fun addressBookDao(): AddressBookDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}