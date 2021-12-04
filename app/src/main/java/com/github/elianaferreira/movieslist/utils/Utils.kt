package com.github.elianaferreira.movieslist.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Utils {
    companion object {
        fun showErrorMessage(context: Context, messsage: String) {
            Toast.makeText(context, messsage, Toast.LENGTH_LONG).show()
        }
    }
}