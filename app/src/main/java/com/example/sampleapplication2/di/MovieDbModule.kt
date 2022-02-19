package com.example.sampleapplication2.di

import android.content.Context
import androidx.annotation.Nullable
import androidx.room.Room
import com.example.sampleapplication2.database.MovieDatabase
import com.example.sampleapplication2.database.MovieDatabase.Companion.DATABASE_NAME
import com.example.sampleapplication2.database.MoviesDao
import com.example.sampleapplication2.database.UpcomingMoviesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MovieDbModule {
    @Singleton
    @Provides
    fun provideMovieDatabase(@Nullable context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java, DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(movieDatabase: MovieDatabase): MoviesDao {
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideUpcomingMoviesDao(movieDatabase: MovieDatabase): UpcomingMoviesDao {
        return movieDatabase.upcomingMovieDao()
    }
}