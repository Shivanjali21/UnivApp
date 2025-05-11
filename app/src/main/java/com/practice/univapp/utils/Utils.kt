package com.practice.univapp.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.practice.univapp.R
import com.practice.univapp.databinding.CustomSnackbarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Context.showCustomSnackBar(message: String, container: View?) {
  container?.let {
    val snackView = View.inflate(this, R.layout.custom_snackbar, null)
    val binding = CustomSnackbarBinding.bind(snackView)
    val snackBar = Snackbar.make(container, "", Snackbar.LENGTH_LONG)
    snackBar.apply {
      snackBar.view.setBackgroundColor(Color.TRANSPARENT)
      (view as ViewGroup).addView(binding.root)
      binding.tvMessage.text = message
      show()
    }
  }
}

fun CoroutineScope.launchOnMain(call:()->Unit){
  launch(Dispatchers.Main) {
    call.invoke()
  }
}