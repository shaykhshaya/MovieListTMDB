package com.example.movieslistapp.presentation.movie_listing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.movieslistapp.databinding.ActivityMainBinding
import com.example.movieslistapp.presentation.movie_listing.adapter.FragmentPageAdapter
import com.example.movieslistapp.util.enums.TabsEnum
import com.example.movieslistapp.util.makeStatusBarTransparent
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FragmentPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.makeStatusBarTransparent()
        setTabTitle()
        initializePageAdapter()
        addTabSelectionListener()
        registerPageChangeListener()
    }

    private fun setTabTitle() {
        binding.tabLayout.apply {
            addTab(newTab().setText(TabsEnum.POPULAR.type))
            addTab(newTab().setText(TabsEnum.LATEST.type))
        }
    }

    private fun registerPageChangeListener() {
        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

    private fun addTabSelectionListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.vp2.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initializePageAdapter() {
        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle)
        binding.vp2.adapter = adapter
    }
}