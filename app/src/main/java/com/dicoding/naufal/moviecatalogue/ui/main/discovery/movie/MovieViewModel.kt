package com.dicoding.naufal.moviecatalogue.ui.main.discovery.movie

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

class MovieViewModel(repo: MovieCatalogRepository) : BaseViewModel(repo) {

    private val movieList = MutableLiveData<Resource<List<FilmEntity>>>()

    init {
        getMovie(1)
    }

    fun getMovie(page: Int = 1) {

        EspressoIdlingResource.increment()
        viewModelScope.launch(Dispatchers.Main) {
            setLoading(true)
            val data = withContext(Dispatchers.IO) {
                repo.getDiscoveryMovie(page)
            }

            movieList.value = data.value

            EspressoIdlingResource.decrement()
        }
    }

    fun getMovieLiveData(): LiveData<Resource<List<FilmEntity>>> {
        return movieList
    }
}