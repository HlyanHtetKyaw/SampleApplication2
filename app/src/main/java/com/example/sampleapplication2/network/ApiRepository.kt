package com.example.sampleapplication2.network

import com.example.sampleapplication2.models.MoviesResponse
import io.reactivex.Single
import javax.inject.Inject

class ApiRepository @Inject
constructor(private val apiService: ApiService) {

    fun getPopularMovies(): Single<MoviesResponse> {
        return apiService.popularMovies()
    }

    fun getUpcomingMovies(): Single<MoviesResponse> {
        return apiService.popularMovies()
    }

}