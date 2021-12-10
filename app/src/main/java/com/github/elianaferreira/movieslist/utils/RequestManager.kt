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
import com.github.elianaferreira.movieslist.list.MoviesList
import com.github.elianaferreira.movieslist.models.*
import com.google.gson.Gson
import java.lang.StringBuilder

class RequestManager(private var context: Context) {

    private val TAG = RequestManager::class.java.simpleName

    private var queue: RequestQueue = Volley.newRequestQueue(context)

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

    private fun makeRequest(progressBar: ProgressBar?, url: String, method: Int, classDTO: Class<*>, successCallback: OnSuccessRequestResult<*>,
                            errorCallback: OnErrorRequestResult) {
        progressBar?.visibility = View.VISIBLE

        if (BuildConfig.DEBUG) Log.d(TAG, "send request to " + url)

        val jsonObjectRequest = JsonObjectRequest(method, url, null,
            { response ->
                if (BuildConfig.DEBUG) Log.d(TAG, "response: " + response.toString())
                progressBar?.visibility = View.GONE
                val parsedResponse = Gson().fromJson(response.toString(), classDTO)
                successCallback.onSuccess(parsedResponse)
            },
            { error ->
                progressBar?.visibility = View.GONE
                if (errorCallback.onError(error)) {
                    error.printStackTrace()
                    //show error
                    Utils.showErrorMessage(context, context.getString(R.string.general_error_message))
                }
            }
        )
        queue.add(jsonObjectRequest)
    }


    private fun getAbsoluteURL(url: String, queryParams: HashMap<String, String>?): String {
        val absoluteURL = StringBuilder()
        absoluteURL.append(BuildConfig.BASE_URL)
        absoluteURL.append(url) //path of request
        absoluteURL.append("?api_key=" + BuildConfig.API_KEY)

        if (queryParams != null) {
            for (key in queryParams.keys) {
                val value = queryParams[key]
                absoluteURL.append("&$key=$value")
            }
        }

        return absoluteURL.toString()
    }


    //used for list of TV Shows as well
    fun getMovies(category: String, page: Int, progressBar: ProgressBar?, successCallback: OnSuccessRequestResult<MoviesList>, errorCallback: OnErrorRequestResult) {
        val queryParams = hashMapOf<String, String>()
        queryParams["page"] = page.toString()
        makeRequest(progressBar, getAbsoluteURL(category, queryParams), Request.Method.GET, MoviesList::class.java, successCallback, errorCallback)
    }

    fun getMovieByID(movieID: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<MovieDetail>, errorCallback: OnErrorRequestResult) {
        makeRequest(progressBar, getAbsoluteURL("movie/$movieID", null), Request.Method.GET,  MovieDetail::class.java, successCallback, errorCallback)
    }

    fun getVideos(movieID: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<Videos>, errorCallback: OnErrorRequestResult) {
        makeRequest(progressBar, getAbsoluteURL("movie/$movieID/videos", null), Request.Method.GET, Videos::class.java, successCallback, errorCallback)
    }

    fun getTVShow(showID: String, progressBar: ProgressBar, successCallback: OnSuccessRequestResult<TVShowDetail>, errorCallback: OnErrorRequestResult) {
        makeRequest(progressBar, getAbsoluteURL("tv/$showID", null), Request.Method.GET, TVShowDetail::class.java, successCallback, errorCallback)
    }
}