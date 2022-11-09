package com.udacity.asteroidradar.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.api.AsteroidsApi
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.getDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Repository(private val database: AsteroidsDatabase)
{
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun refreshAsteroids(startDate: String = getDate(), endDate: String = getDate())
    {
        withContext(Dispatchers.IO)
        {
            val response = AsteroidsApi.retrofitService.getObjectsAsync(startDate, endDate).await()
            val asteroidsList = parseAsteroidsJsonResult(JSONObject(response.string()))
            database.asteroidsDatabaseDao.insertAll(*asteroidsList.asDatabaseModel())
        }
    }

    suspend fun getPictureOfDay() : PictureOfDay?
    {
        var picture : PictureOfDay
        withContext(Dispatchers.IO)
        {
            picture = AsteroidsApi.retrofitService.getPictureOfDayAsync().await()
        }
        if(picture.mediaType == "image")
        {
            return picture
        }
        else
        {
            return null
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun deleteOld()
    {
        withContext(Dispatchers.IO)
        {
            database.asteroidsDatabaseDao.deleteOld(getDate())
        }
    }
}