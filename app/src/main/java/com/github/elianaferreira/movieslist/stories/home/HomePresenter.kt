package com.github.elianaferreira.movieslist.stories.home

import android.view.View
import com.github.elianaferreira.movieslist.utils.BasePresenter

interface HomePresenter: BasePresenter<HomeView> {
    fun setWelcomeMessage()
    fun loadCategories()
    fun categorySelected(category: Category, view: View)
}