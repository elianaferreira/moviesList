package com.github.elianaferreira.movieslist.stories.home

import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.utils.Utils
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.verify


class HomePresenterUnitTest {

    @Mock
    private lateinit var homeView: HomeView

    private lateinit var homePresenter: HomePresenter


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        homePresenter = HomePresenterImpl()
        homePresenter.setView(homeView)
    }

    @Test
    fun executeWelcomeTest() {
        homePresenter.setWelcomeMessage()
        verify(homeView).showWelcomeMessage(Utils.getGreeting())
    }


    @Test
    fun executeLoadCategoriesTest() {
        homePresenter.loadCategories()
        verify(homeView).showCategories(createMockCategories())
    }


    private fun createMockCategories(): List<Category> {
        return listOf<Category>(
            Category("movie/popular", "Popular Movies", R.drawable.img_movie_popular),
            Category("movie/top_rated", "Top Rate Movies", R.drawable.img_movie_top_rated),
            Category("tv/popular", "Popular Series", R.drawable.img_show_popular),
            Category("tv/top_rated", "Top Rate Series", R.drawable.img_show_top_rated)
        )
    }


}