package com.example.sampleapplication2.ui.detail

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapplication2.models.Result
import com.example.sampleapplication2.util.Constants
import com.example.sampleapplication2.util.Constants.Companion.MOVIE_DETAIL
import com.google.gson.Gson
import javax.inject.Inject


class MovieDetailViewModel @Inject
constructor(
) : ViewModel() {

    private val movieDetail = MutableLiveData<Result>()


    internal fun getMovieDetail(): MutableLiveData<Result> {
        return movieDetail
    }

    fun loadMovieDetail(activity: Activity) {
        val mPrefs = activity.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)
        val gSon = Gson()
        val json: String? = mPrefs.getString(MOVIE_DETAIL, "")
        val movie: Result = gSon.fromJson(json, Result::class.java)
        movieDetail.value = movie
    }

}