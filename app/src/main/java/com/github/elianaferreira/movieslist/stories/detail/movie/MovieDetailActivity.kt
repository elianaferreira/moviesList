package com.github.elianaferreira.movieslist.stories.detail.movie

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.github.elianaferreira.movieslist.R
import android.graphics.Color
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.ActionMode
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.elianaferreira.movieslist.BuildConfig
import com.github.elianaferreira.movieslist.stories.detail.movie.di.DaggerMovieDetailComponent
import com.github.elianaferreira.movieslist.stories.detail.movie.di.MovieDetailModule
import com.github.elianaferreira.movieslist.stories.list.Movie
import com.github.elianaferreira.movieslist.utils.Utils
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import javax.inject.Inject


class MovieDetailActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener,
    AppCompatCallback, MovieDetailView {

    companion object {
        const val PARAM_MOVIE = "flag:movieSelected"
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var wrapperMovie: LinearLayout
    private lateinit var wrapperHeader: RelativeLayout
    private lateinit var errorLayout: LinearLayout
    private lateinit var trailerPlayer: LinearLayout
    private lateinit var swipeRelativeLayout: SwipeRefreshLayout

    private lateinit var appCompatDelegate: AppCompatDelegate
    private lateinit var playerView: YouTubePlayerView
    private lateinit var trailerKey: String

    @Inject
    lateinit var movieDetailPresenter: MovieDetailPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        DaggerMovieDetailComponent.builder()
            .movieDetailModule(MovieDetailModule(this))
            .build()
            .inject(this)

        setContentView(R.layout.activity_movie_detail)
        movieDetailPresenter.setView(this)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        appCompatDelegate = AppCompatDelegate.create(this, this)
        appCompatDelegate.onCreate(savedInstanceState)
        appCompatDelegate.setContentView(R.layout.activity_movie_detail)

        val movie = intent.getParcelableExtra<Movie>(PARAM_MOVIE)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        appCompatDelegate.setSupportActionBar(toolbar)
        appCompatDelegate.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        swipeRelativeLayout = findViewById(R.id.refresh_layout)
        progressBar = findViewById(R.id.progress_bar)
        wrapperMovie = findViewById(R.id.wrapper_movie)
        wrapperHeader = findViewById(R.id.wrapper_header)
        wrapperMovie.visibility = View.GONE
        errorLayout = findViewById(R.id.error_layout)
        trailerPlayer = findViewById(R.id.player_trailer)

        playerView = YouTubePlayerView(this)
        playerView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        trailerPlayer.addView(playerView)

        movieDetailPresenter.getMovieDetail(movie?.id.toString())

        Utils.setColorToSwipeRefreh(swipeRelativeLayout)

        swipeRelativeLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            movieDetailPresenter.getMovieDetail(movie?.id.toString())
        })
    }

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
            Utils.showErrorMessage(this, getString(R.string.youtube_player_error))
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
        Utils.loadDataIntoMovieHeader(this@MovieDetailActivity, movieDetail.title, movieDetail, wrapperHeader)
        wrapperMovie.visibility = View.VISIBLE
        movieDetailPresenter.getTrailerPath(movieDetail)
        errorLayout.visibility = View.GONE
    }

    override fun showTrailer(path: String) {
        trailerKey = path
        playerView.initialize(BuildConfig.YOUTUBE_API_KEY, this)
    }

    override fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        swipeRelativeLayout.isRefreshing = false
    }

    override fun showErrorMessage() {
        errorLayout.visibility = View.VISIBLE
    }

    override fun showTrailerView(show: Boolean) {
        trailerPlayer.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    override fun onStop() {
        super.onStop()
        movieDetailPresenter.cancelRequests()
    }
}