package com.github.elianaferreira.movieslist.stories.detail.movie.di

import com.github.elianaferreira.movieslist.stories.detail.movie.MovieDetailActivity
import dagger.Component

@Component(modules = [MovieDetailModule::class])
interface MovieDetailComponent {
    fun inject(activity: MovieDetailActivity)
}