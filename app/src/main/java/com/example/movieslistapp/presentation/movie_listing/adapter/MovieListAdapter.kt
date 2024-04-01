package com.example.movieslistapp.presentation.movie_listing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieslistapp.data.remote.MovieDto
import com.example.movieslistapp.databinding.RowItemMovieBinding
import com.example.movieslistapp.util.Constants
import com.example.movieslistapp.util.enums.PopularityEnum
import com.example.movieslistapp.util.loadImageByUrl
import com.example.movieslistapp.util.visible

class MovieListAdapter(
    private var mList: ArrayList<MovieDto>,
    var callback: (Int?) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: RowItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieDto) {
            binding.apply {
                val imageUrl = if (item.backdropPath.isNullOrEmpty()) {
                    Constants.IMAGE_PLACEHOLDER
                } else {
                    "${Constants.TMDB_IMAGE_BASE_URL}${item.backdropPath}"
                }
                ivMovie.loadImageByUrl(imageUrl)
                tvMovieName.text = item.originalTitle
                tvReleaseDate.text = item.releaseDate
                val voteAverage = (item.voteAverage?.times(10))?.toInt()
                tvProgress.text = voteAverage.toString()

                voteAverage?.let {
                    when {
                        it < PopularityEnum.BAD.grade -> {
                            profileLoaderRed.visible(true)
                            profileLoaderYellow.visible(false)
                            profileLoaderGreen.visible(false)
                            profileLoaderRed.progress = voteAverage
                        }

                        it >= PopularityEnum.BAD.grade && it < PopularityEnum.AVERAGE.grade -> {
                            profileLoaderRed.visible(false)
                            profileLoaderYellow.visible(true)
                            profileLoaderGreen.visible(false)
                            profileLoaderYellow.progress = voteAverage
                        }

                        else -> {
                            profileLoaderRed.visible(false)
                            profileLoaderYellow.visible(false)
                            profileLoaderGreen.visible(true)
                            profileLoaderGreen.progress = voteAverage
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.bind(item)
        holder.binding.clParent.setOnClickListener {
            callback(item.id)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}