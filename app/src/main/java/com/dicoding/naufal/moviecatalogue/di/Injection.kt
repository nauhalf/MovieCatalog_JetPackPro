package com.dicoding.naufal.moviecatalogue.di

import android.app.Application
import com.dicoding.naufal.moviecatalogue.data.MovieCatalogRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.LocalRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.room.MovieCatalogDatabase
import com.dicoding.naufal.moviecatalogue.data.source.remote.RemoteApi
import com.dicoding.naufal.moviecatalogue.data.source.remote.RemoteRepository
import com.dicoding.naufal.moviecatalogue.data.source.remote.RetrofitBuilder


object Injection {
    fun provideRepository(application: Application): MovieCatalogRepository {
        val database = MovieCatalogDatabase.getDatabase(application)
        val localRepository = LocalRepository.getInstance(database)
        val remoteRepository = RemoteRepository.getInstance(provideRemoteApi())

        return MovieCatalogRepository.getInstance(localRepository, remoteRepository)
    }

    fun provideRemoteApi(): RemoteApi {
        return RetrofitBuilder.api()
    }
}