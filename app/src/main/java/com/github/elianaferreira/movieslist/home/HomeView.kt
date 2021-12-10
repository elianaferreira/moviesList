package com.github.elianaferreira.movieslist.home

interface HomeView {
    fun showWelcomeMessage(message: String)
    fun showCategories(categories: List<Category>)
    fun onCategorySelected(category: Category)
}