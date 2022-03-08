package com.github.elianaferreira.movieslist.stories.home

interface HomeView {
    fun showWelcomeMessage(message: Int)
    fun showCategories(categories: List<Category>)
    fun onCategorySelected(category: Category)
}