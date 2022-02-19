package com.example.sampleapplication2.di

import com.example.sampleapplication2.ui.detail.MovieDetailActivity
import com.example.sampleapplication2.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindMovieDetail(): MovieDetailActivity

}