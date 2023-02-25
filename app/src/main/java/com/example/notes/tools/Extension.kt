package com.example.notes.tools

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.notes.R
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Context.toast(message: String){
    Toast.makeText(this , message, Toast.LENGTH_SHORT).show()
}

fun getDayAndTime(): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, H:mm")
    return LocalDateTime.now().format(dateTimeFormatter)
}

fun View.snackbar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun Context.showKeyboard(view : View){
    val keyboard = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    keyboard.showSoftInput(view, 0)
}
