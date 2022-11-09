package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.database.AsteroidsDatabase

class MainViewModelFactory(private val dataSource: AsteroidsDatabase, private val application: Application) : ViewModelProvider.Factory
{
    @RequiresApi(Build.VERSION_CODES.N)
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(dataSource, application) as T
            }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}