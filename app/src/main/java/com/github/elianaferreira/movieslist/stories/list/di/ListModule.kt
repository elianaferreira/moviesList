package com.github.elianaferreira.movieslist.stories.list.di

import android.content.Context
import com.github.elianaferreira.movieslist.stories.list.ListPresenter
import com.github.elianaferreira.movieslist.stories.list.ListPresenterImpl
import com.github.elianaferreira.movieslist.stories.list.ListRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ListModule(context: Context) {

    var mContext: Context = context

    @Provides
    fun provideContext(): Context {
        return mContext
    }

    @Provides
    fun providePresenter(): ListPresenter {
        return ListPresenterImpl(ListRepositoryImpl(mContext))
    }
}