package com.github.elianaferreira.movieslist.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.adapters.GenresAdapter
import com.github.elianaferreira.movieslist.models.*
import com.github.elianaferreira.movieslist.utils.RequestManager
import com.squareup.picasso.Picasso
import java.lang.StringBuilder
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.WindowManager
import com.github.elianaferreira.movieslist.utils.Utils


class MovieDetailActivity : AppCompatActivity() {

    companion object {
        val PARAM_MOVIE = "flag:movieSelected"
    }

    val YOUTUBE_URL_BASE = "https://www.youtube.com/watch?v="

    private lateinit var progressBar: ProgressBar
    private lateinit var wrapperMovie: LinearLayout
    private lateinit var imgMovie: ImageView
    private lateinit var txtTitle: TextView
    private lateinit var txtOverview: TextView
    private lateinit var rvGenres: RecyclerView
    private lateinit var ratingBar: RatingBar
    private lateinit var txtRating: TextView
    private lateinit var txtLanguages: TextView
    private lateinit var btnTrailer: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        val movie = intent.getSerializableExtra(PARAM_MOVIE) as Movie

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
        btnTrailer = findViewById(R.id.btn_trailer)
        btnTrailer.isEnabled = false
        changeTrailerButtonStyle(false)

        val request = RequestManager(this)
        val successCallback = RequestManager.OnSuccessRequestResult<MovieDetail> {
                response ->
            loadData(response as MovieDetail)
        }

        val errorCallback = RequestManager.OnErrorRequestResult { error ->
            error.printStackTrace()
            true
        }

        request.getMovieByID(movie.id.toString(), progressBar, successCallback, errorCallback)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun loadData(movieDetail: MovieDetail) {
        Picasso.get()
            .load(Utils.getPosterURL(movieDetail.backdropPath))
            .placeholder(R.drawable.img_film)
            .error(R.drawable.img_film)
            .into(imgMovie)

        txtTitle.text = movieDetail.title
        txtOverview.text = movieDetail.overview

        val rate = movieDetail.voteAverage
        val rateCount = movieDetail.voteCount
        ratingBar.rating = rate.toFloat()
        txtRating.text = "$rate ($rateCount)"

        rvGenres.layoutManager = GridLayoutManager(this, 3)
        rvGenres.adapter = GenresAdapter(Utils.getGenresNames(movieDetail.genres))
        txtLanguages.text = Utils.getLanguagesConcat(movieDetail.spokenLanguages)

        wrapperMovie.visibility = View.VISIBLE

        if (movieDetail.video) {
            getVideos(movieDetail.id.toString())
        }
    }

    private fun getVideos(movieID: String) {
        val request = RequestManager(this)
        val successCallback = RequestManager.OnSuccessRequestResult<Videos> {
                response ->
            val videosList = response as Videos
            if (videosList != null && videosList.results.isNotEmpty()) {
                btnTrailer.isEnabled = true
                changeTrailerButtonStyle(true)
                btnTrailer.setOnClickListener(View.OnClickListener {
                    val firstTrailerPath = videosList.results.first().key
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(YOUTUBE_URL_BASE + firstTrailerPath)
                        )
                    )
                })
            }
        }

        val errorCallback = RequestManager.OnErrorRequestResult { error ->
            error.printStackTrace()
            btnTrailer.isEnabled = false
            changeTrailerButtonStyle(false)
            false
        }
        request.getVideos(movieID, progressBar, successCallback, errorCallback)
    }

    @SuppressLint("ResourceAsColor")
    private fun changeTrailerButtonStyle(isEnable: Boolean) {
        if (isEnable) {
            btnTrailer.setTextColor(R.color.dark_peach)
        } else {
            btnTrailer.setTextColor(R.color.dark_grey)
        }
    }


}