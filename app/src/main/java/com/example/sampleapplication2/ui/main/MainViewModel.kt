package com.example.sampleapplication2.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapplication2.database.MoviesDbRepository
import com.example.sampleapplication2.database.UpcomingMoviesDbRepository
import com.example.sampleapplication2.models.MoviesDbData
import com.example.sampleapplication2.models.MoviesResponse
import com.example.sampleapplication2.models.Result
import com.example.sampleapplication2.models.UpcomingMoviesDbData
import com.example.sampleapplication2.network.ApiRepository
import com.example.sampleapplication2.util.MainUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject
constructor(
    private val apiRepository: ApiRepository,
    private val moviesDbRepository: MoviesDbRepository,
    private val upcomingMoviesDbRepository: UpcomingMoviesDbRepository
) : ViewModel() {

    private var disposable: CompositeDisposable? = null
    private val popularMovies = MutableLiveData<List<Result>>()
    private val upcomingMovies = MutableLiveData<List<Result>>()
    private val error = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()

    init {
        disposable = CompositeDisposable()
    }

    internal fun getPopularMovies(): LiveData<List<Result>> {
        return popularMovies
    }

    internal fun getUpcomingMovies(): LiveData<List<Result>> {
        return upcomingMovies
    }

    internal fun getLoading(): LiveData<Boolean> {
        return loading
    }

    internal fun getError(): LiveData<Boolean> {
        return error
    }

    fun getPopularMoviesFromApi() {
        loading.value = true
        disposable!!.add(
            apiRepository.getPopularMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<MoviesResponse>() {
                    override fun onSuccess(response: MoviesResponse) {
                        error.value = false
                        popularMovies.value = response.results
                        loading.value = false
                        for (i in response.results!!.indices) {
                            val data = MoviesDbData()
                            data.id = i.toLong()
                            data.adult = response.results[i].adult
                            data.backdropPath = response.results[i].backdropPath
                            data.originalLanguage = response.results[i].originalLanguage
                            data.originalTitle = response.results[i].originalTitle
                            data.overview = response.results[i].overview
                            data.popularity = response.results[i].popularity
                            data.posterPath = response.results[i].posterPath
                            data.releaseDate = response.results[i].releaseDate
                            data.title = response.results[i].title
                            data.video = response.results[i].video
                            data.voteAverage = response.results[i].voteAverage
                            data.voteCount = response.results[i].voteCount

                            moviesDbRepository.insertMovie(data)
                        }

                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: " + e.message)
                        onErrorLoading()
                    }
                })
        )
    }

    fun getUpcomingMoviesFromApi() {
        loading.value = true
        disposable!!.add(
            apiRepository.getUpcomingMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<MoviesResponse>() {
                    override fun onSuccess(response: MoviesResponse) {
                        error.value = false
                        upcomingMovies.value = response.results
                        loading.value = false
                        for (i in response.results!!.indices) {
                            val data = UpcomingMoviesDbData()
                            data.id = i.toLong()
                            data.adult = response.results[i].adult
                            data.backdropPath = response.results[i].backdropPath
                            data.originalLanguage = response.results[i].originalLanguage
                            data.originalTitle = response.results[i].originalTitle
                            data.overview = response.results[i].overview
                            data.popularity = response.results[i].popularity
                            data.posterPath = response.results[i].posterPath
                            data.releaseDate = response.results[i].releaseDate
                            data.title = response.results[i].title
                            data.video = response.results[i].video
                            data.voteAverage = response.results[i].voteAverage
                            data.voteCount = response.results[i].voteCount

                            upcomingMoviesDbRepository.insertMovie(data)
                        }

                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: " + e.message)
                        onErrorLoading()
                    }
                })
        )
    }


    private fun onErrorLoading() {
        error.value = true
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun loadPopularMoviesFromDB(context: Context) {
        loading.value = true
        val moviesDisposable = moviesDbRepository.getAllMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                Log.d(TAG, Gson().toJson(list))
                if (list.isEmpty()) {
                    if (MainUtils.isNetworkAvailable(context)) {
                        getPopularMoviesFromApi()
                    } else {
                        onErrorLoading()
                    }
                } else {
                    this.onAllPopularMoviesFetched(list)
                }
            },
                {
                    onErrorLoading()
                })
        disposable!!.add(moviesDisposable)
    }

    private fun onAllPopularMoviesFetched(movieList: List<MoviesDbData>) {
        val movieResponse = ArrayList<Result>()

        for (movieDbData: MoviesDbData in movieList) {
            val movie = Result()
            movie.adult = movieDbData.adult
            movie.backdropPath = movieDbData.backdropPath
            movie.originalLanguage = movieDbData.originalLanguage
            movie.originalTitle = movieDbData.originalTitle
            movie.overview = movieDbData.overview
            movie.popularity = movieDbData.popularity
            movie.posterPath = movieDbData.posterPath
            movie.releaseDate = movieDbData.releaseDate
            movie.title = movieDbData.title
            movie.video = movieDbData.video
            movie.voteAverage = movieDbData.voteAverage
            movie.voteCount = movieDbData.voteCount
            movieResponse.add(movie)
        }
        popularMovies.value = movieResponse
        loading.value = false
    }

    fun loadUpcomingMoviesFromDB(context: Context) {
        loading.value = true
        val moviesDisposable = upcomingMoviesDbRepository.getAllUpcomingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                Log.d(TAG, Gson().toJson(list))
                if (list.isEmpty()) {
                    if (MainUtils.isNetworkAvailable(context)) {
                        getUpcomingMoviesFromApi()
                    } else {
                        onErrorLoading()
                    }
                } else {
                    this.onAllUpcomingMoviesFetched(list)
                }
            },
                {
                    onErrorLoading()
                })
        disposable!!.add(moviesDisposable)
    }

    private fun onAllUpcomingMoviesFetched(movieList: List<UpcomingMoviesDbData>) {
        val movieResponse = ArrayList<Result>()

        for (movieDbData: UpcomingMoviesDbData in movieList) {
            val movie = Result()
            movie.adult = movieDbData.adult
            movie.backdropPath = movieDbData.backdropPath
            movie.originalLanguage = movieDbData.originalLanguage
            movie.originalTitle = movieDbData.originalTitle
            movie.overview = movieDbData.overview
            movie.popularity = movieDbData.popularity
            movie.posterPath = movieDbData.posterPath
            movie.releaseDate = movieDbData.releaseDate
            movie.title = movieDbData.title
            movie.video = movieDbData.video
            movie.voteAverage = movieDbData.voteAverage
            movie.voteCount = movieDbData.voteCount
            movieResponse.add(movie)
        }

        upcomingMovies.value = movieResponse
        loading.value = false
    }

}