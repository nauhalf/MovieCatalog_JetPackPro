package com.dicoding.naufal.moviecatalogue.ui.main.fav.tv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Resource
import com.dicoding.naufal.moviecatalogue.base.BaseViewModel
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FavoriteEntity
import com.dicoding.naufal.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteTvShowViewModel(repo: MovieCatalogRepository) : BaseViewModel(repo) {
    val favTvLiveData = MutableLiveData<Resource<PagedList<FavoriteEntity>>>()

    init {
        fetchFavTv()
    }

    private fun fetchFavTv() {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            setLoading(true)

            val data = withContext(Dispatchers.IO) {
                repo.getFavoriteFilmPaged(2)
            }

            favTvLiveData.value = data.value
            EspressoIdlingResource.decrement()
        }
    }
}