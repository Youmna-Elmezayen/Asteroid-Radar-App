package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.getDate
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(val database: AsteroidsDatabase, application: Application) : AndroidViewModel(application)
{
    private var _asteroidsList = MutableLiveData<List<Asteroid>?>()
    val asteroidsList: LiveData<List<Asteroid>?> get() = _asteroidsList

    private val repository = Repository(database)

    private val _picture = MutableLiveData<PictureOfDay?>()
    val picture: LiveData<PictureOfDay?> get() = _picture

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetail: LiveData<Asteroid?>
        get() = _navigateToAsteroidDetail

    init {
        viewModelScope.launch {
            try
            {
                repository.refreshAsteroids(getDate(), getDate(7))
                _picture.value = repository.getPictureOfDay()
            }
            catch (e: Exception)
            {
                Toast.makeText(application, "Failure: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
        getTodayAsteroids()

    }

    fun doneNavigating() {
        _navigateToAsteroidDetail.value = null
    }
    fun onItemClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun getTodayAsteroids()
    {
        viewModelScope.launch {
            _asteroidsList.value = database.asteroidsDatabaseDao.getAsteroidsSortedByDate(getDate(), getDate())
        }
    }

    fun getWeekAsteroids()
    {
        viewModelScope.launch {
            _asteroidsList.value = database.asteroidsDatabaseDao.getAsteroidsSortedByDate(getDate(), getDate(7))
        }
    }

    fun getSavedAsteroids()
    {
        viewModelScope.launch {
            _asteroidsList.value = database.asteroidsDatabaseDao.getAllAsteroids()
        }
    }

}