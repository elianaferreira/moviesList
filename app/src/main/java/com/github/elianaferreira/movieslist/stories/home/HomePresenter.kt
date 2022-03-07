package com.github.elianaferreira.movieslist.stories.home

interface HomePresenter {
    fun setView(view: HomeView)
    fun setWelcomeMessage()
    fun loadCategories()
    fun categorySelected(category: Category)
}