package com.example.majika.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.majika.data.dao.MenuDBDao
import com.example.majika.data.entity.MenuDB

@Database(entities = [MenuDB::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun menuDBDao(): MenuDBDao

    companion object {
        const val DATABASE_NAME = "majika-db"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )
                        .build()
                }
            }
            return instance
        }
    }
}