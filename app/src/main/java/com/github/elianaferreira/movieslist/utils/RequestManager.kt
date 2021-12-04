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
import com.github.elianaferreira.movieslist.models.MoviesList
import com.google.gson.Gson
import java.lang.StringBuilder

class RequestManager(private val context: Context) {

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val TAG = RequestManager::class.java.simpleName

    fun interface OnSuccessRequestResult<T> {
        fun onSuccess(response: Any)
    }

    fun interface OnErrorRequestResult {
        fun onError(error: VolleyError): Boolean
    }

    private fun sendRequest(progressBar: ProgressBar, url: String, method: Int, params: HashMap<String, String>?, classDTO: Class<*>, successCallback: OnSuccessRequestResult<*>,
                            errorCallback: OnErrorRequestResult) {
        val queue: RequestQueue = Volley.newRequestQueue(context)

        progressBar.visibility = View.VISIBLE

        var absoluteURL = StringBuilder()
        absoluteURL.append(BASE_URL)
        absoluteURL.append(url) //path of request
        absoluteURL.append("?api_key=" + API_KEY)

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
                    //show error
                    Utils.showErrorMessage(context, "Error al obtener los datos")
                }
            }
        )
        queue.add(jsonObjectRequest)
    }


    fun getMovies(progressBar: ProgressBar, category: String, successCallback: OnSuccessRequestResult<MoviesList>, errorCallback: OnErrorRequestResult) {

        sendRequest(progressBar, category, Request.Method.GET, null, MoviesList::class.java, successCallback, errorCallback)
    }

}