package com.kaankilic.film.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kaankilic.film.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao


    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }

        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "FoodDatabase"

        )
            .build()


    }
}