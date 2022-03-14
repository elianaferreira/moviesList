package com.github.elianaferreira.movieslist.stories.list

import android.view.View
import com.github.elianaferreira.movieslist.utils.BasePresenter

interface ListPresenter: BasePresenter<ListView> {
    fun getList(category: String, page: Int)
    fun itemSelected(movie: Movie, view: View)
}