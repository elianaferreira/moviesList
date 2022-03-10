package com.github.elianaferreira.movieslist.utils

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.elianaferreira.movieslist.BuildConfig
import com.github.elianaferreira.movieslist.R
import com.google.gson.Gson
import java.lang.StringBuilder

open class RequestManager(private var context: Context) {

    private val TAG = RequestManager::class.java.simpleName //use the same log TAG for requests

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

    protected fun makeRequest(url: String, method: Int, classDTO: Class<*>, successCallback: OnSuccessRequestResult<*>,
                            errorCallback: OnErrorRequestResult) {

        if (BuildConfig.DEBUG) Log.d(TAG, "send request to " + url)

        val jsonObjectRequest = JsonObjectRequest(method, url, null,
            { response ->
                if (BuildConfig.DEBUG) Log.d(TAG, "response: " + response.toString())
                val parsedResponse = Gson().fromJson(response.toString(), classDTO)
                successCallback.onSuccess(parsedResponse)
            },
            { error ->
                if (errorCallback.onError(error)) {
                    Log.e(RequestManager::class.simpleName, error.cause?.message, error)
                    //show error
                    Utils.showErrorMessage(context, context.getString(R.string.general_error_message))
                }
            }
        )
        jsonObjectRequest.tag = TAG
        queue.add(jsonObjectRequest)
    }


    protected fun getAbsoluteURL(url: String, queryParams: HashMap<String, String>?): String {
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

    protected fun cancelAllRequest() {
        Log.d(TAG, "cancelAllRequest called")
        queue.cancelAll(TAG)
    }
}