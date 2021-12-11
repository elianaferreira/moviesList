package com.github.elianaferreira.movieslist.stories.list

import com.github.elianaferreira.movieslist.utils.RequestManager

class ListPresenterImpl(private val listView: ListView, private val requestManager: RequestManager): ListPresenter {

    private val successCallback = RequestManager.OnSuccessRequestResult<MoviesList> {
            response ->
        listView.showProgressBar(false)
        val moviesList = response as MoviesList
        if (moviesList.page == 1) {
            listView.showList(response)
        } else {
            listView.addMoreItems(moviesList.results as MutableList<Movie>)
        }
    }

    private val errorCallback = RequestManager.OnErrorRequestResult { error ->
        error.printStackTrace()
        listView.showProgressBar(false)
        true
    }

    override fun getList(category: String, page: Int) {
        listView.showProgressBar(true)
        requestManager.getMovies(category, page, successCallback, errorCallback)
    }

    override fun itemSelected(movie: Movie) {
        listView.onMovieSelected(movie)
    }
}