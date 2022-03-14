package com.github.elianaferreira.movieslist.stories.home.di

import android.content.Context
import com.github.elianaferreira.movieslist.stories.home.HomePresenter
import com.github.elianaferreira.movieslist.stories.home.HomePresenterImpl
import dagger.Module
import dagger.Provides

@Module
class HomeModule(context: Context) {

    var mContext: Context = context

    @Provides
    fun provideContext(): Context {
        return mContext
    }

    @Provides
    fun providesHomePresenter(): HomePresenter {
        return HomePresenterImpl()
    }
}