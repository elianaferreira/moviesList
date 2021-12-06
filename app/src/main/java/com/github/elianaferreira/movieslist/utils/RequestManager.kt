package com.github.elianaferreira.movieslist.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.elianaferreira.movieslist.BuildConfig
import com.github.elianaferreira.movieslist.models.MovieDetail
import com.github.elianaferreira.movieslist.models.MoviesList
import com.github.elianaferreira.movieslist.models.TVShowDetail
import com.github.elianaferreira.movieslist.models.Videos
import com.google.gson.Gson
import java.lang.StringBuilder

class RequestManager(private val context: Context) {

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val TAG = RequestManager::class.java.simpleName

    fun interface OnSuccessRequestResult<T> {
        fun onSuccess(response: Any)
    }

    fun interface OnErrorRequestResult {
        //TODO put explanation of this
        fun onError(error: VolleyError): Boolean
    }

    private fun sendRequest(progressBar: ProgressBar, url: String, method: Int, params: HashMap<String, String>?, classDTO: Class<*>, successCallback: OnSuccessRequestResult<*>,
                            errorCallback: OnErrorRequestResult) {
        val queue: RequestQueue = Volley.newRequestQueue(context)

        progressBar.visibility = View.VISIBLE

        val absoluteURL = StringBuilder()
        absoluteURL.append(BASE_URL)
        absoluteURL.append(url) //path of request
        absoluteURL.append("?api_key=" + API_KEY)

        if (BuildConfig.DEBUG) Log.d(TAG, "send request to " + absoluteURL.toString())

        //TODO transform 'params' into JSON and pass to the object request as parameter

        val jsonObjectRequest = JsonObjectRequest(method, absoluteURL.toString(), null,
            { response ->
                if (BuildConfig.DEBUG) Log.d(TAG, "response: " + response.toString())
                progressBar.visibility = View.GONE
                val parsedResponse = Gson().fromJson(response.toString(), classDTO)
                successCallback.onSuccess(parsedResponse)
            },
            { error ->
                progressBar.visibility = View.GONE
                if (errorCallback.onError(error)) {
                    //show error
                    Utils.showErrorMessage(context, "Error al obtener los datos")
                }
            }
        )
        queue.add(jsonObjectRequest)
    }


    //used for list of TV Shows as well
    fun getMovies(category: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<MoviesList>, errorCallback: OnErrorRequestResult) {
        sendRequest(progressBar, category, Request.Method.GET, null, MoviesList::class.java, successCallback, errorCallback)
    }

    fun getMovieByID(movieID: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<MovieDetail>, errorCallback: OnErrorRequestResult) {
        sendRequest(progressBar, "movie/$movieID", Request.Method.GET, null, MovieDetail::class.java, successCallback, errorCallback)
    }

    fun getVideos(movieID: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<Videos>, errorCallback: OnErrorRequestResult) {
        sendRequest(progressBar, "movie/$movieID/videos", Request.Method.GET, null, Videos::class.java, successCallback, errorCallback)
    }

    fun getTVShow(showID: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<TVShowDetail>, errorCallback: OnErrorRequestResult) {
        sendRequest(progressBar, "tv/$showID", Request.Method.GET, null, TVShowDetail::class.java, successCallback, errorCallback)
    }
}