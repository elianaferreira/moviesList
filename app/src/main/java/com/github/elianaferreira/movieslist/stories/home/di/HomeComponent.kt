package com.github.elianaferreira.movieslist.stories.home.di

import com.github.elianaferreira.movieslist.stories.home.MainActivity
import dagger.Component

@Component(modules = [HomeModule::class])
interface HomeComponent {
    fun inject(activity: MainActivity)
}