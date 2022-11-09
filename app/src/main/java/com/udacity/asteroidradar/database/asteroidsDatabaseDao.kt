package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidsDatabaseDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: Asteroid)
    @Query("SELECT * FROM asteroids_table ORDER BY closeApproachDate ASC")
    suspend fun getAllAsteroids() : List<Asteroid>?
    @Query("SELECT * FROM asteroids_table WHERE id = :id")
    suspend fun getAsteroid(id: Long) : Asteroid?
    @Query("SELECT * FROM asteroids_table WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    suspend fun getAsteroidsSortedByDate(startDate: String, endDate: String): List<Asteroid>?
    @Query("DELETE FROM asteroids_table WHERE closeApproachDate < :oldDate")
    suspend fun deleteOld(oldDate: String)
}