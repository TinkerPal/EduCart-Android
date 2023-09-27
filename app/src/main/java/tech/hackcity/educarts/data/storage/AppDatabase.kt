package tech.hackcity.educarts.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.hackcity.educarts.domain.dao.CountryDao
import tech.hackcity.educarts.domain.model.auth.User
import tech.hackcity.educarts.domain.model.location.Country
import tech.hackcity.educarts.uitls.DatabaseConverters

/**
 *Created by Victor Loveday on 9/19/23
 */

@Database(entities = [Country::class], version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "EducCartsDatabase.db"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}
