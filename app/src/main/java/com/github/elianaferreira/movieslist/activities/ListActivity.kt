package com.github.elianaferreira.movieslist.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.adapters.MoviesAdapter
import com.github.elianaferreira.movieslist.models.Category
import com.github.elianaferreira.movieslist.models.MoviesList
import com.github.elianaferreira.movieslist.utils.RequestManager
import com.github.elianaferreira.movieslist.utils.Utils

class ListActivity : AppCompatActivity() {

    companion object {
        val PARAM_LIST_TYPE = "flag:listType"
    }

    private lateinit var rvMovies: RecyclerView
    private lateinit var category: Category
    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        category = intent.getSerializableExtra(PARAM_LIST_TYPE) as Category

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = category.categoryName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        rvMovies = findViewById(R.id.list_movies)
        rvMovies.layoutManager = LinearLayoutManager(this)

        val request = RequestManager(this)
        val successCallback = RequestManager.OnSuccessRequestResult<MoviesList> {
                response ->
            populateList(response as MoviesList)
        }

        val errorCallback = RequestManager.OnErrorRequestResult { error ->
            Log.d(">>>>>", "onCreate: ha ocurrido un error")
            error.printStackTrace()
            true
        }

        request.getMovies(category.categoryValue, progressBar, successCallback, errorCallback)
    }

    private fun populateList(moviesList: MoviesList) {
        adapter = MoviesAdapter(Utils.categoryIsMovie(category.categoryValue), moviesList.results) {
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