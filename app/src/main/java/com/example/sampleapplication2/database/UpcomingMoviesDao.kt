package com.example.sampleapplication2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleapplication2.models.MoviesDbData
import com.example.sampleapplication2.models.UpcomingMoviesDbData
import io.reactivex.Single

@Dao
interface UpcomingMoviesDao {
    @Query("SELECT * FROM upcoming_movie_table")
    fun getAllUpcomingMovies(): Single<List<UpcomingMoviesDbData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(moviesDbData: UpcomingMoviesDbData)
}