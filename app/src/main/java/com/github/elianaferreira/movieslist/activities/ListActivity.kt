package com.github.elianaferreira.movieslist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
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
        rvMovies.adapter = MoviesAdapter(moviesList.results) {
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
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}