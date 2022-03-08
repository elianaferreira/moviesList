package com.github.elianaferreira.movieslist.stories.detail.tvshow.di

import android.content.Context
import com.github.elianaferreira.movieslist.stories.detail.tvshow.ShowDetailPresenter
import com.github.elianaferreira.movieslist.stories.detail.tvshow.ShowDetailPresenterImpl
import com.github.elianaferreira.movieslist.stories.detail.tvshow.TVShowRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ShowDetailModule(context: Context) {

    var mContext: Context = context

    @Provides
    fun provideContext(): Context {
        return mContext
    }

    @Provides
    fun providePresenter(): ShowDetailPresenter {
        return ShowDetailPresenterImpl(TVShowRepositoryImpl(mContext))
    }
}