package com.github.elianaferreira.movieslist.stories.list

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.stories.detail.movie.MovieDetailActivity
import com.github.elianaferreira.movieslist.stories.detail.tvshow.TVShowDetailActivity
import com.github.elianaferreira.movieslist.stories.home.Category
import com.github.elianaferreira.movieslist.stories.list.di.DaggerListComponent
import com.github.elianaferreira.movieslist.stories.list.di.ListModule
import com.github.elianaferreira.movieslist.utils.Utils
import javax.inject.Inject

class ListActivity : AppCompatActivity(), ListView {

    companion object {
        const val PARAM_LIST_TYPE = "flag:listType"
    }

    private lateinit var rvMovies: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorLayout: LinearLayout

    private var category: Category? = null
    private lateinit var adapter: MoviesAdapter

    @Inject
    lateinit var listPresenter: ListPresenter

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerListComponent.builder()
            .listModule(ListModule(this))
            .build()
            .inject(this)

        setContentView(R.layout.activity_list)

        listPresenter.setView(this)

        category = intent.getParcelableExtra<Category?>(PARAM_LIST_TYPE)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = category?.categoryName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        errorLayout = findViewById(R.id.error_layout)
        progressBar = findViewById(R.id.progress_bar)
        rvMovies = findViewById(R.id.list_movies)
        rvMovies.layoutManager = LinearLayoutManager(this)

        if (category != null) {
            listPresenter.getList(category!!.categoryValue, page)
        }

        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = (rvMovies.layoutManager as LinearLayoutManager).childCount
                val pastVisibleItem = (rvMovies.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount

                if ((visibleItemCount + pastVisibleItem) >= total) {
                    listPresenter.getList(category!!.categoryValue, page++)
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
                if (this@ListActivity::adapter.isInitialized) {
                    adapter.filter.filter(newText)
                }
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun showList(list: MoviesList) {
        adapter = MoviesAdapter(category!!.categoryIsMovie(), list.results as MutableList<Movie>) {
            movie, view ->
            listPresenter.itemSelected(movie, view)
        }
        rvMovies.adapter = adapter
    }

    override fun addMoreItems(list: MutableList<Movie>) {
        adapter.addData(list)
    }

    override fun onMovieSelected(movie: Movie, view: View) {
        if (category!!.categoryIsMovie()) {
            val intent = Intent(this@ListActivity, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.PARAM_MOVIE, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@ListActivity,
                view,
                getString(R.string.transition_name)
            )
            startActivity(intent, options.toBundle())
        } else {
            val intent = Intent(this@ListActivity, TVShowDetailActivity::class.java)
            intent.putExtra(TVShowDetailActivity.PARAM_SHOW, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@ListActivity,
                view,
                getString(R.string.transition_name)
            )
            startActivity(intent, options.toBundle())
        }
    }


    override fun showProgressBar(show: Boolean) {
        this.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrorMessage() {
        if (this@ListActivity::adapter.isInitialized) {
            if (adapter.itemCount == 0) {
                errorLayout.visibility = View.VISIBLE
            } else {
                showErrorByDefault()
            }
        } else {
            errorLayout.visibility = View.VISIBLE
        }
    }

    private fun showErrorByDefault() {
        errorLayout.visibility = View.GONE
        Utils.showErrorMessage(this@ListActivity, getString(R.string.general_error_message))
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}