package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid

@Database(entities = [Asteroid::class], version = 2)
abstract class AsteroidsDatabase : RoomDatabase()
{
    abstract val asteroidsDatabaseDao: AsteroidsDatabaseDao
    companion object {

        private lateinit var INSTANCE: AsteroidsDatabase

        fun getInstance(context: Context): AsteroidsDatabase {
            synchronized(AsteroidsDatabase::class.java)
            {
                if (!::INSTANCE.isInitialized)
                {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AsteroidsDatabase::class.java, "asteroids_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}