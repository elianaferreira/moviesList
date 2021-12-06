package com.github.elianaferreira.movieslist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.adapters.GenresAdapter
import com.github.elianaferreira.movieslist.models.Movie
import com.github.elianaferreira.movieslist.models.MovieDetail
import com.github.elianaferreira.movieslist.models.TVShowDetail
import com.github.elianaferreira.movieslist.utils.RequestManager
import com.github.elianaferreira.movieslist.utils.Utils
import com.squareup.picasso.Picasso

class TVShowDetailActivity : AppCompatActivity() {

    companion object {
        val PARAM_SHOW = "flag:showSelected"
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        val tvShow = intent.getSerializableExtra(PARAM_SHOW) as Movie

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = tvShow.name
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

        val request = RequestManager(this)
        val successCallback = RequestManager.OnSuccessRequestResult<TVShowDetail> {
                response ->
            val tvShow = response as TVShowDetail
            loadData(tvShow)
        }

        val errorCallback = RequestManager.OnErrorRequestResult { error ->
            Log.d(">>>>>", "onCreate: ha ocurrido un error")
            error.printStackTrace()
            true
        }

        request.getTVShow(tvShow.id.toString(), progressBar, successCallback, errorCallback)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun loadData(showDetail: TVShowDetail) {
        Picasso.get()
            .load(Utils.getPosterURL(showDetail.backdropPath))
            .placeholder(R.drawable.img_film)
            .error(R.drawable.img_film)
            .into(imgMovie)

        txtTitle.text = showDetail.name
        txtOverview.text = showDetail.overview

        val rate = showDetail.voteAverage
        val rateCount = showDetail.voteCount
        ratingBar.rating = rate.toFloat()
        txtRating.text = "$rate ($rateCount)"

        rvGenres.layoutManager = GridLayoutManager(this, 3)
        rvGenres.adapter = GenresAdapter(Utils.getGenresNames(showDetail.genres))
        txtLanguages.text = Utils.getLanguagesConcat(showDetail.spokenLanguages)

        wrapperMovie.visibility = View.VISIBLE

        //seasons
        for(season in showDetail.seasons) {
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
}