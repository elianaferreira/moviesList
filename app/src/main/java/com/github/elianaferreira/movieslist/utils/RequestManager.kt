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
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.models.*
import com.google.gson.Gson
import java.lang.StringBuilder

class RequestManager(private var context: Context) {

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val TAG = RequestManager::class.java.simpleName

    private var queue: RequestQueue = Volley.newRequestQueue(context)

    private val application = App.instance

    fun interface OnSuccessRequestResult<T> {
        fun onSuccess(response: Any)
    }

    fun interface OnErrorRequestResult {
        /**
         * Sometimes is preferred not to show error message to the user,
         * in this cases this method should return FALSE, in case of return TRUE
         * the error manager of  makeRequest will execute the callback and then
         * show the error message to the user.
         */
        fun onError(error: VolleyError): Boolean
    }

    private fun getApiKey(progressBar: ProgressBar, url: String, method: Int, classDTO: Class<*>, successCallback: OnSuccessRequestResult<*>,
                          errorCallback: OnErrorRequestResult) {

        val apiURL = "http://moviedbapikeyprovider.herokuapp.com/api_key"

        progressBar.visibility = View.VISIBLE

        if (BuildConfig.DEBUG) Log.d(TAG, "send request to " + apiURL)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, apiURL, null,
            { response ->
                if (BuildConfig.DEBUG) Log.d(TAG, "response: " + response.toString())
                //FIXME: it could be just a string
                val parsedResponse = Gson().fromJson(response.toString(), ApiKey::class.java)
                application.setApiKey(parsedResponse.apiKey)
                makeRequest(progressBar, url, method, classDTO, successCallback, errorCallback)
            },
            { error ->
                if (errorCallback.onError(error)) {
                    progressBar.visibility = View.GONE
                    //show error
                    Utils.showErrorMessage(context, context.getString(R.string.general_error_message))
                }
            }
        )
        queue.add(jsonObjectRequest)
    }


    private fun sendRequest(progressBar: ProgressBar, url: String, method: Int, classDTO: Class<*>, successCallback: OnSuccessRequestResult<*>,
                                        errorCallback: OnErrorRequestResult) {
        if (application.getApiKey() != null) {
            makeRequest(progressBar, url, method, classDTO, successCallback, errorCallback)
        } else {
            getApiKey(progressBar, url, method, classDTO, successCallback, errorCallback)
        }
    }


    private fun makeRequest(progressBar: ProgressBar, url: String, method: Int, classDTO: Class<*>, successCallback: OnSuccessRequestResult<*>,
                            errorCallback: OnErrorRequestResult) {
        progressBar.visibility = View.VISIBLE

        val absoluteURL = StringBuilder()
        absoluteURL.append(BASE_URL)
        absoluteURL.append(url) //path of request
        absoluteURL.append("?api_key=" + application.getApiKey())

        if (BuildConfig.DEBUG) Log.d(TAG, "send request to " + absoluteURL.toString())

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
                    error.printStackTrace()
                    //show error
                    Utils.showErrorMessage(context, context.getString(R.string.general_error_message))
                }
            }
        )
        queue.add(jsonObjectRequest)
    }


    //used for list of TV Shows as well
    fun getMovies(category: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<MoviesList>, errorCallback: OnErrorRequestResult) {
        sendRequest(progressBar, category, Request.Method.GET, MoviesList::class.java, successCallback, errorCallback)
    }

    fun getMovieByID(movieID: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<MovieDetail>, errorCallback: OnErrorRequestResult) {
        sendRequest(progressBar, "movie/$movieID", Request.Method.GET,  MovieDetail::class.java, successCallback, errorCallback)
    }

    fun getVideos(movieID: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<Videos>, errorCallback: OnErrorRequestResult) {
        sendRequest(progressBar, "movie/$movieID/videos", Request.Method.GET, Videos::class.java, successCallback, errorCallback)
    }

    fun getTVShow(showID: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<TVShowDetail>, errorCallback: OnErrorRequestResult) {
        sendRequest(progressBar, "tv/$showID", Request.Method.GET, TVShowDetail::class.java, successCallback, errorCallback)
    }
}