package com.example.sampleapplication2.database

import com.example.sampleapplication2.models.MoviesDbData
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesDbRepository @Inject
constructor(private val moviesDao: MoviesDao) {

    fun getAllMovies(): Single<List<MoviesDbData>> {
        return moviesDao.getAllMovies()
    }

    fun insertMovie(moviesDbData: MoviesDbData) {
        moviesDao.insert(moviesDbData)
    }

}