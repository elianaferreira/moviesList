package com.github.elianaferreira.movieslist.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.adapters.MoviesAdapter
import com.github.elianaferreira.movieslist.home.Category
import com.github.elianaferreira.movieslist.models.Movie
import com.github.elianaferreira.movieslist.models.MoviesList
import com.github.elianaferreira.movieslist.utils.RequestManager
import com.github.elianaferreira.movieslist.utils.Utils

class ListActivity : AppCompatActivity() {

    companion object {
        const val PARAM_LIST_TYPE = "flag:listType"
    }

    private lateinit var rvMovies: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var category: Category
    private lateinit var adapter: MoviesAdapter
    private lateinit var request: RequestManager

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        category = intent.getSerializableExtra(PARAM_LIST_TYPE) as Category

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = category.categoryName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.progress_bar)
        rvMovies = findViewById(R.id.list_movies)
        rvMovies.layoutManager = LinearLayoutManager(this)

        request = RequestManager(this)

        getMoviesFromAPI(page)

        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = (rvMovies.layoutManager as LinearLayoutManager).childCount
                val pastVisibleItem = (rvMovies.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount

                if ((visibleItemCount + pastVisibleItem) >= total) {
                    getMoviesFromAPI(page++)
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }


    private fun getMoviesFromAPI(page: Int) {
        val successCallback = RequestManager.OnSuccessRequestResult<MoviesList> {
                response ->
           if (page == 1) {
               populateList(response as MoviesList)
           } else {
               val moreMovies = response as MoviesList
               adapter.addData(moreMovies.results as MutableList<Movie>)
           }
        }

        val errorCallback = RequestManager.OnErrorRequestResult { error ->
            error.printStackTrace()
            true
        }

        request.getMovies(category.categoryValue, page, progressBar, successCallback, errorCallback)
    }


    private fun populateList(moviesList: MoviesList) {
        adapter = MoviesAdapter(Utils.categoryIsMovie(category.categoryValue), moviesList.results as MutableList<Movie>) {
            movie ->
            if (Utils.categoryIsMovie(category.categoryValue)) {
                val intent = Intent(this@ListActivity, MovieDetailActivity::class.java)
                intent.putExtra(MovieDetailActivity.PARAM_MOVIE, movie)
                startActivity(intent)
            } else {
                val intent = Intent(this@ListActivity, TVShowDetailActivity::class.java)
                intent.putExtra(TVShowDetailActivity.PARAM_SHOW, movie)
                startActivity(intent)
            }
        }
        rvMovies.adapter = adapter
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}