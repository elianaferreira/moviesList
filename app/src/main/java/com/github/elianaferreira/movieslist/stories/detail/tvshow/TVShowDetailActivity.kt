package com.github.elianaferreira.movieslist.stories.detail.tvshow

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.stories.detail.movie.Genre
import com.github.elianaferreira.movieslist.stories.detail.movie.SpokenLanguage
import com.github.elianaferreira.movieslist.stories.list.Movie
import com.github.elianaferreira.movieslist.utils.Utils
import com.squareup.picasso.Picasso

class TVShowDetailActivity : AppCompatActivity(), ShowDetailView {

    companion object {
        const val PARAM_SHOW = "flag:showSelected"
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var wrapperMovie: LinearLayout
    private lateinit var imgMovie: ImageView
    private lateinit var txtTitle: TextView
    private lateinit var txtOverview: TextView
    private lateinit var rvGenres: RecyclerView
    private lateinit var ratingBar: RatingBar
    private lateinit var txtRating: TextView
    private lateinit var txtLanguages: TextView
    private lateinit var wrapperSeasons: LinearLayout
    private lateinit var errorLayout: LinearLayout

    private lateinit var showDetailPresenter: ShowDetailPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        val tvShow = intent.getParcelableExtra<Movie>(PARAM_SHOW)
        val repository = TVShowRepositoryImpl(this)
        showDetailPresenter = ShowDetailPresenterImpl(this, repository)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.progress_bar)
        wrapperMovie = findViewById(R.id.wrapper_movie)
        wrapperMovie.visibility = View.GONE
        imgMovie = findViewById(R.id.img_movie)
        txtTitle = findViewById(R.id.tv_movie)
        txtOverview = findViewById(R.id.tv_overview)
        rvGenres = findViewById(R.id.rv_genres)
        ratingBar = findViewById(R.id.item_rate)
        txtRating = findViewById(R.id.item_rate_value)
        txtLanguages = findViewById(R.id.txt_languages)
        wrapperSeasons = findViewById(R.id.wrapper_seasons)
        errorLayout = findViewById(R.id.error_layout)

        showDetailPresenter.getShowDetail(tvShow?.id.toString())
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun showTVShowDetail(tvShowDetail: TVShowDetail) {
        Picasso.get()
            .load(Utils.getPosterURL(tvShowDetail.backdropPath))
            .placeholder(R.drawable.img_film)
            .error(R.drawable.img_film)
            .into(imgMovie)

        txtTitle.text = tvShowDetail.name
        txtOverview.text = tvShowDetail.overview

        val rate = tvShowDetail.voteAverage
        val rateCount = tvShowDetail.voteCount
        ratingBar.rating = rate.toFloat()
        txtRating.text = "$rate ($rateCount)"

        rvGenres.layoutManager = GridLayoutManager(this, 3)
        rvGenres.adapter = GenresAdapter(Genre.getGenresNames(tvShowDetail.genres))
        txtLanguages.text = SpokenLanguage.getLanguagesConcat(tvShowDetail.spokenLanguages)

        wrapperMovie.visibility = View.VISIBLE

        //seasons
        for(season in tvShowDetail.seasons) {
            val card: View = LayoutInflater.from(this@TVShowDetailActivity).inflate(R.layout.card_season, null)
            val imgPoster: ImageView = card.findViewById(R.id.img_poster)
            val txtTitle: TextView = card.findViewById(R.id.txt_title)
            val txtOverview: TextView = card.findViewById(R.id.txt_overview)
            Picasso.get()
                .load(Utils.getPosterURL(season.posterPath))
                .placeholder(R.drawable.img_film)
                .error(R.drawable.img_film)
                .into(imgPoster)
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
    }
}