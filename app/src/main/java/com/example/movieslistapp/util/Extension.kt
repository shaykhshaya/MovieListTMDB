package com.example.movieslistapp.util

import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.movieslistapp.R

fun ImageView.loadImageByUrl(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 50f
    circularProgressDrawable.setColorSchemeColors(
        ContextCompat.getColor(
            this.context,
            R.color.blue
        )
    )
    circularProgressDrawable.start()
    Glide
        .with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(circularProgressDrawable)
        .into(this)
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Int.formatDuration(): String {
    val hours = this / 60
    val remainingMinutes = this % 60
    return "${hours}h ${if (remainingMinutes > 0) "${remainingMinutes}m" else ""}"
}

fun Window.makeStatusBarTransparent() {
    this.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )

}


