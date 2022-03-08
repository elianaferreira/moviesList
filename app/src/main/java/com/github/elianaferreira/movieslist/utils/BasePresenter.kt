package com.github.elianaferreira.movieslist.utils

interface BasePresenter<T> {
    fun setView(view: T)
}