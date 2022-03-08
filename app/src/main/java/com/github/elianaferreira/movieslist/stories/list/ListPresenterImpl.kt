package com.github.elianaferreira.movieslist.stories.list

import com.github.elianaferreira.movieslist.utils.RequestManager

class ListPresenterImpl(var repository: ListRepository): ListPresenter {

    private lateinit var listView: ListView

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
        listView.showErrorMessage()
        true
    }

    override fun getList(category: String, page: Int) {
        listView.showProgressBar(true)
        repository.getMovies(category, page, successCallback, errorCallback)
    }

    override fun itemSelected(movie: Movie) {
        listView.onMovieSelected(movie)
    }

    override fun setView(view: ListView) {
        listView = view
    }
}