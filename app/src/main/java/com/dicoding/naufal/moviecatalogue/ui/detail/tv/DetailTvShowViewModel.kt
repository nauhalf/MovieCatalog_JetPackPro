package com.dicoding.naufal.moviecatalogue.ui.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Resource
import com.dicoding.naufal.moviecatalogue.base.BaseViewModel
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FavoriteEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.TvEntity
import com.dicoding.naufal.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTvShowViewModel(repo: MovieCatalogRepository) : BaseViewModel(repo) {

    private val tvLiveData = MutableLiveData<Resource<TvEntity>>()

    private val isFavorite = MutableLiveData<Boolean>()

    fun getIsFavorite(): LiveData<Boolean> = isFavorite

    fun addToFavorite() {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.addFavorite(
                    FavoriteEntity(
                        filmId = tvLiveData.value?.data?.id as Int,
                        title = tvLiveData.value?.data?.title,
                        posterPath = tvLiveData.value?.data?.posterPath,
                        voteAverage = tvLiveData.value?.data?.voteAverage as Double,
                        type = 2
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
                repo.deleteFavoriteTv(tvLiveData.value?.data?.id as Int)
            }

            isFavorite.value = false
            EspressoIdlingResource.decrement()
        }
    }

    private fun fetchFav(tvId: Int) {
        viewModelScope.launch {
            val isFav: FavoriteEntity? = withContext(Dispatchers.IO) {
                repo.getFavorite(tvId, 2)
            }

            isFavorite.value = isFav != null
        }
    }

    fun getTvLiveData(): LiveData<Resource<TvEntity>> = tvLiveData

    fun setTvLiveData(tvId: Int) {
        if (tvLiveData.value == null) {
            fetchTv(tvId)
            fetchFav(tvId)
        }
    }


    private fun fetchTv(tvId: Int) {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            setLoading(true)
            val tvResult = withContext(Dispatchers.IO) {
                repo.getTv(tvId)
            }

            tvLiveData.value = tvResult.value

            EspressoIdlingResource.decrement()
        }
    }
}