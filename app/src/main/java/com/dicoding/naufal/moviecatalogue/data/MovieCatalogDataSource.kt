package com.dicoding.naufal.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Resource
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FavoriteEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FilmEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.TvEntity

interface MovieCatalogDataSource {
    suspend fun getDiscoveryMovie(page: Int): LiveData<Resource<List<FilmEntity>>>

    suspend fun getDiscoveryTv(page: Int): LiveData<Resource<List<FilmEntity>>>

    suspend fun getTv(tvId: Int): LiveData<Resource<TvEntity>>

    suspend fun getMovie(movieId: Int): LiveData<Resource<MovieEntity>>

    suspend fun addFavorite(favoriteEntity: FavoriteEntity)

    suspend fun deleteFavoriteMovie(filmId: Int)

    suspend fun deleteFavoriteTv(filmId: Int)

    suspend fun getFavorite(filmId: Int, type: Int): FavoriteEntity

    suspend fun getFavoriteFilmPaged(type: Int): LiveData<Resource<PagedList<FavoriteEntity>>>
}