package com.dicoding.naufal.moviecatalogue.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository
import com.dicoding.naufal.moviecatalogue.di.Injection
import com.dicoding.naufal.moviecatalogue.ui.detail.movie.DetailMovieViewModel
import com.dicoding.naufal.moviecatalogue.ui.detail.tv.DetailTvShowViewModel
import com.dicoding.naufal.moviecatalogue.ui.main.discovery.movie.MovieViewModel
import com.dicoding.naufal.moviecatalogue.ui.main.fav.movie.FavoriteMovieViewModel
import com.dicoding.naufal.moviecatalogue.ui.main.fav.tv.FavoriteTvShowViewModel
import com.dicoding.naufal.moviecatalogue.ui.main.tv.TvShowViewModel


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repo: MovieCatalogRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(
                repo
            ) as T
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> TvShowViewModel(
                repo
            ) as T
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> DetailMovieViewModel(
                repo
            ) as T
            modelClass.isAssignableFrom(DetailTvShowViewModel::class.java) -> DetailTvShowViewModel(
                repo
            ) as T

            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> FavoriteMovieViewModel(
                repo
            ) as T

            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> FavoriteTvShowViewModel(
                repo
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = ViewModelFactory(Injection.provideRepository(application))
                INSTANCE = instance
                return instance
            }
        }
    }

}