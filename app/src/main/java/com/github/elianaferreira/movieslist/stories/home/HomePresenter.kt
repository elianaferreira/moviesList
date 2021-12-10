package com.github.elianaferreira.movieslist.stories.home

interface HomePresenter {
    fun setWelcomeMessage()
    fun loadCategories()
    fun categorySelected(category: Category)
}