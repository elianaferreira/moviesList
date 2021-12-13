package com.github.elianaferreira.movieslist.stories.detail.tvshow

import com.github.elianaferreira.movieslist.utils.ProgressBarView

interface ShowDetailView: ProgressBarView {
    fun showTVShowDetail(tvShowDetail: TVShowDetail)
    fun showErrorMessage()
}