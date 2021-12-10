package com.github.elianaferreira.movieslist.list

interface ListPresenter {

    fun getList(category: String, page: Int)
    fun itemSelected(movie: Movie)
}