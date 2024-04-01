package com.example.movieslistapp.presentation.movie_listing.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieslistapp.presentation.movie_listing.LatestMoviesFragment
import com.example.movieslistapp.presentation.movie_listing.PopularMoviesFragment

class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            PopularMoviesFragment()
        } else {
            LatestMoviesFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}