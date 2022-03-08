package com.github.elianaferreira.movieslist.stories.detail.tvshow

import com.github.elianaferreira.movieslist.utils.BasePresenter

interface ShowDetailPresenter: BasePresenter<ShowDetailView> {
    fun getShowDetail(showID: String)
}