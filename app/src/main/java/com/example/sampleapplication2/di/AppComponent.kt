package com.example.sampleapplication2.di

import android.app.Application
import com.example.sampleapplication2.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [ContextModule::class, ApplicationModule::class,
        AndroidSupportInjectionModule::class, ActivityBindingModule::class, MovieDbModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication?> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): AppComponent?
    }
}

