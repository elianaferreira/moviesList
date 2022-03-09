package com.github.elianaferreira.movieslist.stories.home

import android.view.View

interface HomeView {
    fun showWelcomeMessage(message: Int)
    fun showCategories(categories: List<Category>)
    fun onCategorySelected(category: Category, view: View)
}