package com.example.sampleapplication2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleapplication2.models.MoviesDbData
import io.reactivex.Single

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movie_table")
    fun getAllMovies(): Single<List<MoviesDbData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(moviesDbData: MoviesDbData)
}