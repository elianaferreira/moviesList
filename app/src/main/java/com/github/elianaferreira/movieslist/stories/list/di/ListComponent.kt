package com.github.elianaferreira.movieslist.stories.list.di

import com.github.elianaferreira.movieslist.stories.list.ListActivity
import dagger.Component

@Component(modules = [ListModule::class])
interface ListComponent {
    fun inject(activity: ListActivity)
}