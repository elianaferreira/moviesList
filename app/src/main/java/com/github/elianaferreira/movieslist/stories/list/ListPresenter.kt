package com.github.elianaferreira.movieslist.stories.list

interface ListPresenter {

    fun getList(category: String, page: Int)
    fun itemSelected(movie: Movie)
}