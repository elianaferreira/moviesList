package com.github.elianaferreira.movieslist.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class RequestManager(private val context: Context) {

    private val BASE_URL = "https://api.themoviedb.org/"

    fun interface OnSuccessRequestResult<T> {
        fun onSuccess(response: Any)
    }

    fun interface OnErrorRequestResult {
        fun onError(error: VolleyError): Boolean
    }

    private fun sendRequest(progressBar: ProgressBar, method: Int, url: String, params: HashMap<String, String>?, classDTO: Class<*>, successCallback: OnSuccessRequestResult<*>,
                            errorCallback: OnErrorRequestResult) {
        var queue: RequestQueue = Volley.newRequestQueue(context)

        progressBar.visibility = View.VISIBLE

        val jsonObjectRequest = JsonObjectRequest(method, url, null,
            { response ->
                progressBar.visibility = View.GONE
                successCallback.onSuccess(response)
            },
            { error ->
                progressBar.visibility = View.GONE
                errorCallback.onError(error)
            }
        )
        queue.add(jsonObjectRequest)
    }

}