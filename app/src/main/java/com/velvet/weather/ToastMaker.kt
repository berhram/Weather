package com.velvet.weather

import android.content.Context
import android.widget.Toast

class ToastMaker(private val context: Context) {
    fun makeToast(textId: Int) {
        val message = context.getString(textId)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}