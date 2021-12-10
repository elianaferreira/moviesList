package com.github.elianaferreira.movieslist.home

interface HomePresenter {
    fun setWelcomeMessage()
    fun loadCategories()
    fun categorySelected(category: Category)
}