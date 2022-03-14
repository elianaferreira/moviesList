package com.github.elianaferreira.movieslist.stories.detail.tvshow.di

import com.github.elianaferreira.movieslist.stories.detail.tvshow.TVShowDetailActivity
import dagger.Component

@Component(modules = [ShowDetailModule::class])
interface ShowDetailComponent {
    fun inject(activity: TVShowDetailActivity)
}