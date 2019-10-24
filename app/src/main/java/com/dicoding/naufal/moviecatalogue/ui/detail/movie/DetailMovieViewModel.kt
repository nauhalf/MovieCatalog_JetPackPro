package com.dicoding.naufal.moviecatalogue.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Resource
import com.dicoding.naufal.moviecatalogue.base.BaseViewModel
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FavoriteEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.naufal.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailMovieViewModel(repo: MovieCatalogRepository) : BaseViewModel(repo) {

    private val movieLiveData = MutableLiveData<Resource<MovieEntity>>()

    fun getMovieLiveData(): LiveData<Resource<MovieEntity>> = movieLiveData

    private val isFavorite = MutableLiveData<Boolean>()

    fun getIsFavorite(): LiveData<Boolean> = isFavorite

    fun addToFavorite() {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.addFavorite(
                    FavoriteEntity(
                        filmId = movieLiveData.value?.data?.id as Int,
                        title = movieLiveData.value?.data?.title,
                        posterPath = movieLiveData.value?.data?.posterPath,
                        voteAverage = movieLiveData.value?.data?.voteAverage as Double,
                        type = 1
                    )
                )
            }

            isFavorite.value = true
            EspressoIdlingResource.decrement()
        }
    }


    fun deleteToFavorite() {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.deleteFavoriteTv(movieLiveData.value?.data?.id as Int)
            }

            isFavorite.value = false
            EspressoIdlingResource.decrement()
        }
    }

    private fun fetchFav(movieId: Int) {
        viewModelScope.launch {
            val isFav: FavoriteEntity? = withContext(Dispatchers.IO) {
                repo.getFavorite(movieId, 1)
            }

            isFavorite.value = isFav != null
        }
    }

    fun setMovieLiveData(movieId: Int) {
        if (movieLiveData.value == null) {
            fetchMovie(movieId)
            fetchFav(movieId)
        }
    }

    private fun fetchMovie(movieId: Int) {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            setLoading(true)
            val movieResult = withContext(Dispatchers.IO) {
                repo.getMovie(movieId)
            }

            movieLiveData.value = movieResult.value
            EspressoIdlingResource.decrement()
        }
    }
}