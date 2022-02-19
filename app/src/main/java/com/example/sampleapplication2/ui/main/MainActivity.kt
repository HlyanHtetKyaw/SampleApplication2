package com.example.sampleapplication2.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapplication2.databinding.ActivityMainBinding
import com.example.sampleapplication2.models.Result
import com.example.sampleapplication2.ui.base.ViewBindingActivity
import com.example.sampleapplication2.viewModels.ViewModelFactory
import javax.inject.Inject

class MainActivity : ViewBindingActivity<ActivityMainBinding>() {

    companion object {
        private var viewModel: MainViewModel? = null
        private lateinit var mBinding: ActivityMainBinding
        private var upcomingMoviesAdapter: UpcomingMoviesAdapter? = null
        private var popularMoviesAdapter: PopularMoviesAdapter? = null
        private var mActivity: AppCompatActivity? = null
        private const val TAG = "MainActivity"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = binding
        mActivity = this
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        viewModel!!.getLoading().observe(
            this,
            LoadingObserver()
        )
        viewModel!!.getPopularMovies().observe(
            this,
            PopularMoviesObserver()
        )
        viewModel!!.getUpcomingMovies().observe(
            this,
            UpcomingMoviesObserver()
        )
        viewModel!!.getError().observe(
            this,
            ErrorObserver()
        )
        viewModel!!.loadPopularMoviesFromDB(this)
        viewModel!!.loadUpcomingMoviesFromDB(this)

    }

    private class PopularMoviesObserver : Observer<List<Result>?> {
        override fun onChanged(movieList: List<Result>?) {
            if (movieList == null) return
            popularMoviesAdapter = PopularMoviesAdapter(
                mActivity!!, movieList
            )
            val llm = LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
            mBinding.rvPopularMovies.layoutManager = llm
            mBinding.rvPopularMovies.adapter =
                popularMoviesAdapter
        }
    }

    private class UpcomingMoviesObserver : Observer<List<Result>?> {
        override fun onChanged(movieList: List<Result>?) {
            if (movieList == null) return
            upcomingMoviesAdapter = UpcomingMoviesAdapter(
                mActivity!!, movieList
            )
            val llm = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
            mBinding.rvUpcomingMovies.layoutManager = llm
            mBinding.rvUpcomingMovies.adapter =
                upcomingMoviesAdapter
        }
    }

    private class LoadingObserver : Observer<Boolean?> {
        override fun onChanged(@Nullable isLoading: Boolean?) {
            if (isLoading == null) return
            if (isLoading) {
                mBinding.progressBar.visibility = View.VISIBLE
            } else {
                mBinding.progressBar.visibility = View.GONE
            }
        }
    }

    private class ErrorObserver : Observer<Boolean?> {
        override fun onChanged(@Nullable isError: Boolean?) {
            if (isError == null) return
            if (isError) {
                Log.d(TAG, "OnError:")
            }
        }
    }

}