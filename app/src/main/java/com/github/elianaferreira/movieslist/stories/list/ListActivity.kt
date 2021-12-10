package com.github.elianaferreira.movieslist.stories.list

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.stories.detail.MovieDetailActivity
import com.github.elianaferreira.movieslist.stories.detail.TVShowDetailActivity
import com.github.elianaferreira.movieslist.stories.home.Category
import com.github.elianaferreira.movieslist.utils.RequestManager
import com.github.elianaferreira.movieslist.utils.Utils

class ListActivity : AppCompatActivity(), ListView {

    companion object {
        const val PARAM_LIST_TYPE = "flag:listType"
    }

    private lateinit var rvMovies: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var category: Category
    private lateinit var adapter: MoviesAdapter
    private lateinit var request: RequestManager
    private lateinit var listPresenter: ListPresenter

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        request = RequestManager(this)
        listPresenter = ListPresenterImpl(this, request)
        category = intent.getSerializableExtra(PARAM_LIST_TYPE) as Category

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = category.categoryName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.progress_bar)
        rvMovies = findViewById(R.id.list_movies)
        rvMovies.layoutManager = LinearLayoutManager(this)

        listPresenter.getList(category.categoryValue, page)

        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = (rvMovies.layoutManager as LinearLayoutManager).childCount
                val pastVisibleItem = (rvMovies.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount

                if ((visibleItemCount + pastVisibleItem) >= total) {
                    listPresenter.getList(category.categoryValue, page++)
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
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

    override fun showList(list: MoviesList) {
        adapter = MoviesAdapter(Utils.categoryIsMovie(category.categoryValue), list.results as MutableList<Movie>) {
            movie ->
            listPresenter.itemSelected(movie)
        }
        rvMovies.adapter = adapter
    }

    override fun addMoreItems(list: MutableList<Movie>) {
        adapter.addData(list)
    }

    override fun onMovieSelected(movie: Movie) {
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


    override fun showProgressBar() {
        this.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        this.progressBar.visibility = View.GONE
    }
}