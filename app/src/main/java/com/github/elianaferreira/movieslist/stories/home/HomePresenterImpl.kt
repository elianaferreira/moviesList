package com.github.elianaferreira.movieslist.stories.home

import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.utils.Utils

class HomePresenterImpl: HomePresenter {

    private lateinit var homeView: HomeView

    override fun setWelcomeMessage() {
        homeView.showWelcomeMessage(Utils.getGreeting())
    }

    override fun loadCategories() {
        val categories = listOf<Category>(
            Category("movie/popular", "Popular Movies", R.drawable.img_movie_popular),
            Category("movie/top_rated", "Top Rate Movies", R.drawable.img_movie_top_rated),
            Category("tv/popular", "Popular Series", R.drawable.img_show_popular),
            Category("tv/top_rated", "Top Rate Series", R.drawable.img_show_top_rated)
        )
        homeView.showCategories(categories)
    }

    override fun categorySelected(category: Category) {
        homeView.onCategorySelected(category)
    }

    override fun setView(view: HomeView) {
        this.homeView = view
    }
}