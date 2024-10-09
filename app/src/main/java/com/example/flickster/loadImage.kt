package com.example.flickster

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load("https://image.tmdb.org/t/p/w500$imageUrl")
        .centerCrop()
        .placeholder(R.drawable.dummy_image)
        .into(view)
}

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("progressVoteAverage")
    fun setVoteAverageProgress(progressBar: ProgressBar, voteAverage: Double) {
        val progress = (voteAverage * 10).toInt()
        progressBar.progress = progress
    }

    @JvmStatic
    @BindingAdapter("textVoteAverage")
    fun setVoteAverageText(textView: TextView, voteAverage: Double) {
        val percentage = (voteAverage * 10).toInt()
        textView.text = percentage.toString()
    }
}