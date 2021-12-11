package com.github.elianaferreira.movieslist.stories.detail

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.models.*
import com.github.elianaferreira.movieslist.utils.RequestManager
import com.squareup.picasso.Picasso
import android.graphics.Color
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.ActionMode
import com.github.elianaferreira.movieslist.BuildConfig
import com.github.elianaferreira.movieslist.stories.list.Movie
import com.github.elianaferreira.movieslist.utils.Utils
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView


class MovieDetailActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener,
    AppCompatCallback, MovieDetailView {

    companion object {
        const val PARAM_MOVIE = "flag:movieSelected"
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
    private lateinit var errorLayout: LinearLayout
    private lateinit var trailerPlayer: LinearLayout

    private lateinit var appCompatDelegate: AppCompatDelegate
    private lateinit var playerView: YouTubePlayerView
    private lateinit var trailerKey: String
    private lateinit var movieDetailPresenter: MovieDetailPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        appCompatDelegate = AppCompatDelegate.create(this, this)
        appCompatDelegate.onCreate(savedInstanceState)
        appCompatDelegate.setContentView(R.layout.activity_movie_detail)

        val request = RequestManager(this)

        movieDetailPresenter = MovieDetailPresenterImpl(this, request)

        val movie = intent.getSerializableExtra(PARAM_MOVIE) as Movie

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        appCompatDelegate.setSupportActionBar(toolbar)
        appCompatDelegate.supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
        errorLayout = findViewById(R.id.error_layout)
        trailerPlayer = findViewById(R.id.player_trailer)

//        val successCallback = RequestManager.OnSuccessRequestResult<MovieDetail> {
//                response ->
//            errorLayout.visibility = View.GONE
//            loadData(response as MovieDetail)
//        }
//
//        val errorCallback = RequestManager.OnErrorRequestResult { error ->
//            error.printStackTrace()
//            errorLayout.visibility = View.VISIBLE
//            false
//        }

        //TODO
        movieDetailPresenter.getMovieDetail(movie.id.toString())

        playerView = YouTubePlayerView(this)
        playerView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        trailerPlayer.addView(playerView)
    }


//    private fun getVideos(movieID: String) {
//        val request = RequestManager(this)
//        val successCallback = RequestManager.OnSuccessRequestResult<Videos> {
//                response ->
//            val videosList = response as Videos
//            if (videosList.results.isNotEmpty()) {
//                trailerPlayer.visibility = View.VISIBLE
//                trailerKey = Utils.getTrailerKey(videosList.results)
//                playerView.initialize(BuildConfig.YOUTUBE_API_KEY, this)
//            }
//        }
//
//        val errorCallback = RequestManager.OnErrorRequestResult { error ->
//            error.printStackTrace()
//            trailerPlayer.visibility = View.GONE
//            false
//        }
//        request.getVideos(movieID, successCallback, errorCallback)
//    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener {
            override fun onLoading() {
            }

            override fun onLoaded(p0: String?) {
            }

            override fun onAdStarted() {
            }

            override fun onVideoStarted() {
            }

            override fun onVideoEnded() {
            }

            override fun onError(p0: YouTubePlayer.ErrorReason?) {
            }

        }

        val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {
            override fun onPlaying() {
            }

            override fun onPaused() {
            }

            override fun onStopped() {
            }

            override fun onBuffering(p0: Boolean) {
            }

            override fun onSeekTo(p0: Int) {
            }

        }


        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)

        if (!wasRestored) {
            youTubePlayer?.cueVideo(trailerKey)
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(this, 0).show()
        } else {
            val errorMessage = "There was an error initializing the YoutubePlayer ($youTubeInitializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }

    }

    override fun onSupportActionModeStarted(mode: ActionMode?) {
    }

    override fun onSupportActionModeFinished(mode: ActionMode?) {
    }

    override fun onWindowStartingSupportActionMode(callback: ActionMode.Callback?): ActionMode? {
        return null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showMovieDetail(movieDetail: MovieDetail) {
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

        movieDetailPresenter.getTrailerPath(movieDetail)
    }

    override fun showTrailer(path: String) {
        trailerKey = path
        playerView.initialize(BuildConfig.YOUTUBE_API_KEY, this)
    }

    override fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrorMessage() {
        errorLayout.visibility = View.VISIBLE
    }

    override fun showTrailerView(show: Boolean) {
        trailerPlayer.visibility = if (show) View.VISIBLE else View.GONE
    }
}