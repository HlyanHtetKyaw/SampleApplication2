package com.example.sampleapplication2.ui.detail

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sampleapplication2.R
import com.example.sampleapplication2.databinding.ActivityMovieDetailBinding
import com.example.sampleapplication2.models.Result
import com.example.sampleapplication2.ui.base.ViewBindingActivity
import com.example.sampleapplication2.viewModels.ViewModelFactory
import javax.inject.Inject

class MovieDetailActivity : ViewBindingActivity<ActivityMovieDetailBinding>() {
    companion object {
        private var viewModel: MovieDetailViewModel? = null
        private var mActivity: Activity? = null
        private lateinit var mBinding: ActivityMovieDetailBinding

    }


    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override fun onCreateViewBinding() = ActivityMovieDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        mBinding = binding
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)
        viewModel!!.getMovieDetail().observe(this, MovieDetailObserver())
        viewModel!!.loadMovieDetail(this)

        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private class MovieDetailObserver : Observer<Result?> {
        override fun onChanged(result: Result?) {
            if (result == null) return
            mBinding.tvTitle.text = result.title
            mBinding.tvName.text = result.title
            mBinding.tvLanguage.text = result.originalLanguage
            mBinding.tvReleaseDate.text = result.releaseDate
            mBinding.tvDesc.text = result.overview
            Glide.with(mActivity!!).load(result.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate().fitCenter().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(mBinding.ivMovie)
        }
    }


}