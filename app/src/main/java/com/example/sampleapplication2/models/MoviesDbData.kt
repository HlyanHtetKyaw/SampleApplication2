package com.example.sampleapplication2.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_table")
class MoviesDbData {

    @PrimaryKey
    var id: Long = 0

    var adult: Boolean? = null
    var backdropPath: String? = null
    var originalLanguage: String? = null
    var originalTitle: String? = null
    var overview: String? = null
    var popularity: Double? = null
    var posterPath: String? = null
    var releaseDate: String? = null
    var title: String? = null
    var video: Boolean? = null
    var voteAverage: Double? = null
    var voteCount: Int? = null
    var isFavorite: Boolean? = null
}