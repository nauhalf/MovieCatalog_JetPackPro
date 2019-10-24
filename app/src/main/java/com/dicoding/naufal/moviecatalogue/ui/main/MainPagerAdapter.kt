package com.dicoding.naufal.moviecatalogue.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.ui.main.movie.MovieFragment
import com.dicoding.naufal.moviecatalogue.ui.main.tv.TvShowFragment

class MainPagerAdapter(fragmentManager: FragmentManager, val context: Context) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieFragment()
            else -> TvShowFragment()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.movies)
            else -> context.getString(R.string.tv_shows)
        }
    }
}