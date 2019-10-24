package com.dicoding.naufal.moviecatalogue.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository

open class BaseViewModel(protected val repo: MovieCatalogRepository) : ViewModel() {


    private val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun setLoading(state: Boolean) {
        loadingLiveData.postValue(state)
    }

    fun isLoading(): LiveData<Boolean> = loadingLiveData
}