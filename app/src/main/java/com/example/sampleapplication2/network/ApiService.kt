package com.example.sampleapplication2.network

import com.example.sampleapplication2.models.MoviesResponse
import com.example.sampleapplication2.util.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("upcoming")
    fun upcomingMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Single<MoviesResponse>

    @GET("popular")
    fun popularMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Single<MoviesResponse>

}
