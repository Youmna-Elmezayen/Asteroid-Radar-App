package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface AsteroidsApiService
{
   @GET("neo/rest/v1/feed")
   fun getObjectsAsync(
       @Query("start_date") startDate: String,
       @Query("end_date") endDate: String,
       @Query("api_key") apiKey: String = Constants.API_KEY
   ): Deferred<ResponseBody>

   @GET("planetary/apod")
   fun getPictureOfDayAsync(@Query("api_key")apiKey: String = Constants.API_KEY
   ): Deferred<PictureOfDay>
}

object AsteroidsApi
{
    val retrofitService: AsteroidsApiService by lazy {
        retrofit.create(AsteroidsApiService::class.java)
    }
}