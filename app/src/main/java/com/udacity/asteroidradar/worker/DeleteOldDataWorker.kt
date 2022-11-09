package com.udacity.asteroidradar.worker


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.repository.Repository
import retrofit2.HttpException

class DeleteOldDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "DeleteOldData"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun doWork(): Result {
        val database = AsteroidsDatabase.getInstance(applicationContext)
        val repository = Repository(database)
        return try {
            repository.deleteOld()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
