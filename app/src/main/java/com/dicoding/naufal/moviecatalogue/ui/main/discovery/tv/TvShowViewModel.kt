package com.dicoding.naufal.moviecatalogue.ui.main.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Resource
import com.dicoding.naufal.moviecatalogue.base.BaseViewModel
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FilmEntity
import com.dicoding.naufal.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TvShowViewModel(repo: MovieCatalogRepository) : BaseViewModel(repo) {
    private val tvLiveData = MutableLiveData<Resource<List<FilmEntity>>>()

    init {
        getTv(1)
    }

    fun getTv(page: Int = 1) {
        EspressoIdlingResource.increment()
        viewModelScope.launch(Dispatchers.Main) {
            setLoading(true)

            val data = withContext(Dispatchers.IO) {
                repo.getDiscoveryTv(page)
            }

            tvLiveData.value = data.value

            setLoading(false)
            EspressoIdlingResource.decrement()
        }
    }

    fun getTvLiveData(): LiveData<Resource<List<FilmEntity>>> {
        return tvLiveData
    }
}