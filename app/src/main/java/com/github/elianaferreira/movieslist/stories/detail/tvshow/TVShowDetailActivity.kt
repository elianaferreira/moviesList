package com.github.elianaferreira.movieslist.stories.detail.tvshow

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.stories.detail.tvshow.di.DaggerShowDetailComponent
import com.github.elianaferreira.movieslist.stories.detail.tvshow.di.ShowDetailModule
import com.github.elianaferreira.movieslist.stories.list.Movie
import com.github.elianaferreira.movieslist.utils.ImageLoader
import com.github.elianaferreira.movieslist.utils.Utils
import javax.inject.Inject

class TVShowDetailActivity : AppCompatActivity(), ShowDetailView {

    companion object {
        const val PARAM_SHOW = "flag:showSelected"
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var wrapperMovie: LinearLayout
    private lateinit var wrapperHeader: RelativeLayout
    private lateinit var wrapperSeasons: LinearLayout
    private lateinit var errorLayout: LinearLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @Inject
    lateinit var showDetailPresenter: ShowDetailPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerShowDetailComponent.builder()
            .showDetailModule(ShowDetailModule(this))
            .build()
            .inject(this)

        setContentView(R.layout.activity_tvshow_detail)
        showDetailPresenter.setView(this)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        val tvShow = intent.getParcelableExtra<Movie>(PARAM_SHOW)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.progress_bar)
        wrapperMovie = findViewById(R.id.wrapper_movie)
        wrapperHeader = findViewById(R.id.wrapper_header)
        wrapperMovie.visibility = View.GONE
        wrapperSeasons = findViewById(R.id.wrapper_seasons)
        errorLayout = findViewById(R.id.error_layout)
        swipeRefreshLayout = findViewById(R.id.refresh_layout)
        Utils.setColorToSwipeRefreh(swipeRefreshLayout)

        showDetailPresenter.getShowDetail(tvShow?.id.toString())

        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            showDetailPresenter.getShowDetail(tvShow?.id.toString())
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun showTVShowDetail(tvShowDetail: TVShowDetail) {
        Utils.loadDataIntoMovieHeader(this@TVShowDetailActivity, tvShowDetail.name, tvShowDetail, wrapperHeader)
        wrapperMovie.visibility = View.VISIBLE
        errorLayout.visibility = View.GONE

        //seasons
        for(season in tvShowDetail.seasons) {
            val card: View = LayoutInflater.from(this@TVShowDetailActivity).inflate(R.layout.card_season, null)
            val imgPoster: ImageView = card.findViewById(R.id.img_poster)
            val txtTitle: TextView = card.findViewById(R.id.txt_title)
            val txtOverview: TextView = card.findViewById(R.id.txt_overview)
            ImageLoader.loadImage(Utils.getPosterURL(season.posterPath), imgPoster)
            txtTitle.text = season.name
            txtOverview.text = season.overview
            wrapperSeasons.addView(card)
        }
    }


    override fun showErrorMessage() {
        errorLayout.visibility = View.VISIBLE
    }

    
    override fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    override fun onStop() {
        super.onStop()
        showDetailPresenter.cancelRequests()
    }
}