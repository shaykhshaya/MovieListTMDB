package com.example.movieslistapp.presentation.movie_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieslistapp.data.remote.MovieDetailResponse
import com.example.movieslistapp.data.remote.ProductionCompany
import com.example.movieslistapp.databinding.ActivityMovieDetailBinding
import com.example.movieslistapp.presentation.movie_detail.adapter.CastCrewAdapter
import com.example.movieslistapp.util.BuildNetworkClient
import com.example.movieslistapp.util.Constants
import com.example.movieslistapp.util.formatDuration
import com.example.movieslistapp.util.loadImageByUrl
import com.example.movieslistapp.util.makeStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private var mCastCrewAdapter: CastCrewAdapter? = null
    private var mCompanyList = arrayListOf<ProductionCompany>()

    companion object {
        const val PARAM_MOVIE_ID = "param_movie_id"

        @JvmStatic
        fun start(
            context: Context,
            movieId: Int
        ) {
            val starter = Intent(context, MovieDetailActivity::class.java)
                .putExtra(PARAM_MOVIE_ID, movieId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeMovieListAdapter()
        window.makeStatusBarTransparent()
        observeMovieDetails()
        getMovieDetailsBasedOnId()

        /**click_event*/
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun initializeMovieListAdapter() {
        mCastCrewAdapter = CastCrewAdapter(mCompanyList)
        binding.rvCastCrew.apply {
            adapter = mCastCrewAdapter
            layoutManager =
                LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setViews(response: MovieDetailResponse) {
        binding.apply {
            val imageUrl = "${Constants.TMDB_IMAGE_BASE_URL}${response.backdropPath}"
            val posterUrl = "${Constants.TMDB_IMAGE_BASE_URL}${response.posterPath}"
            val movieTitle = response.originalTitle
            val genres = response.genres?.map { it.name }
            val genresString = genres?.joinToString(", ")
            val year = response.releaseDate
            val duration = response.runtime
            val averageVote = response.voteAverage
            val tagline = response.tagline
            val overView = response.overview

            tvMovieName.text = movieTitle
            tvTitleDescription.text = "${year}-${genresString}-${duration?.formatDuration()}"
            averageVote?.let { tvProgress.text = (it * 10).toInt().toString() }
            tagline?.let { tvMovieType.text = it }
            overView?.let { tvOverviewDescription.text = it }
            ivPosterSmall.loadImageByUrl(imageUrl.ifEmpty { Constants.IMAGE_PLACEHOLDER })
            ivPosterLarge.loadImageByUrl(posterUrl.ifEmpty { Constants.IMAGE_PLACEHOLDER })
        }
    }

    private fun getMovieDetailsBasedOnId(page: String = "") {
        val movieId = intent?.getIntExtra(PARAM_MOVIE_ID, 0)
        movieId?.let {
            BuildNetworkClient.build(
                page = page,
                endPoint = Constants.END_POINT_DETAIL,
                path = it.toString()
            )
            viewModel.getMovieDetails()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeMovieDetails() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mMovieDetailsSharedFlow.collectLatest { response ->
                    if (response != null) {
                        mCompanyList.clear()
                        response.productionCompanies?.let { mCompanyList.addAll(it) }
                        mCastCrewAdapter?.notifyDataSetChanged()
                        setViews(response)
                    }
                }
            }
        }
    }
}