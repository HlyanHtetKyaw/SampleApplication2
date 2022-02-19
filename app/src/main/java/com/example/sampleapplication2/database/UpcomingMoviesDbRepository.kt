package com.example.sampleapplication2.database

import com.example.sampleapplication2.models.UpcomingMoviesDbData
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpcomingMoviesDbRepository @Inject
constructor(private val upcomingMoviesDao: UpcomingMoviesDao) {

    fun getAllUpcomingMovies(): Single<List<UpcomingMoviesDbData>> {
        return upcomingMoviesDao.getAllUpcomingMovies()
    }

    fun insertMovie(moviesDbData: UpcomingMoviesDbData) {
        upcomingMoviesDao.insert(moviesDbData)
    }

}