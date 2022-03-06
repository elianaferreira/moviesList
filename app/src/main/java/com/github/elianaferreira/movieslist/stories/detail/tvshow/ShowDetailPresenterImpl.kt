package com.github.elianaferreira.movieslist.stories.detail.tvshow

import com.github.elianaferreira.movieslist.utils.RequestManager

class ShowDetailPresenterImpl(private val showDetailView: ShowDetailView, private val repository: TVShowRepository): ShowDetailPresenter {

    private val successCallback = RequestManager.OnSuccessRequestResult<TVShowDetail> {
            response ->
        showDetailView.showProgressBar(false)
        showDetailView.showTVShowDetail(response as TVShowDetail)
    }

    private val errorCallback = RequestManager.OnErrorRequestResult { error ->
        error.printStackTrace()
        showDetailView.showProgressBar(false)
        showDetailView.showErrorMessage()
        false
    }

    override fun getShowDetail(showID: String) {
        showDetailView.showProgressBar(true)
        repository.getTVShow(showID, successCallback, errorCallback)
    }
}