package com.example.sampleapplication2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sampleapplication2.models.MoviesDbData
import com.example.sampleapplication2.models.UpcomingMoviesDbData

@Database(
    entities = [MoviesDbData::class, UpcomingMoviesDbData::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MoviesDao

    abstract fun upcomingMovieDao(): UpcomingMoviesDao

    companion object {
        const val DATABASE_NAME = "movie.db"
    }
}