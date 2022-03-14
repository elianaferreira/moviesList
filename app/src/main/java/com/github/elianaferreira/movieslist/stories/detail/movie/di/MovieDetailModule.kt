package com.github.elianaferreira.movieslist.stories.detail.movie.di

import android.content.Context
import com.github.elianaferreira.movieslist.stories.detail.movie.MovieDetailPresenter
import com.github.elianaferreira.movieslist.stories.detail.movie.MovieDetailPresenterImpl
import com.github.elianaferreira.movieslist.stories.detail.movie.MovieDetailRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class MovieDetailModule(context: Context) {

    var mContext: Context = context

    @Provides
    fun provideContext(): Context {
        return mContext
    }

    @Provides
    fun providePresenter(): MovieDetailPresenter {
        return MovieDetailPresenterImpl(MovieDetailRepositoryImpl(mContext))
    }


}