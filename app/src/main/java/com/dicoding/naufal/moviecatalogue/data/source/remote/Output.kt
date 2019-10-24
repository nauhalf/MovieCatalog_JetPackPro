package com.dicoding.naufal.moviecatalogue.data.source.remote

sealed class Output<T>{
    data class Success<T>(val output: T) : Output<T>()
    data class Error(val exception: Exception) : Output<Nothing>()
}