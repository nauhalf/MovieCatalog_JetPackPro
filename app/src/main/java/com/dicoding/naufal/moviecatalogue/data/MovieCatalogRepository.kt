package com.dicoding.naufal.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Resource
import com.dicoding.naufal.moviecatalogue.data.source.local.LocalRepository
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FavoriteEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FilmEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.TvEntity
import com.dicoding.naufal.moviecatalogue.data.source.remote.ApiResponse
import com.dicoding.naufal.moviecatalogue.data.source.remote.RemoteRepository
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.FilmResponse
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.MovieResponse
import com.dicoding.naufal.moviecatalogue.data.source.remote.response.TvResponse

class MovieCatalogRepository(
    private val local: LocalRepository,
    private val remote: RemoteRepository
) :
    MovieCatalogDataSource {
    override suspend fun getDiscoveryMovie(page: Int): LiveData<Resource<List<FilmEntity>>> {
        return object : NetworkBoundResource<List<FilmEntity>, List<FilmResponse>>() {
            override suspend fun loadFromDB(): List<FilmEntity>? {
                return local.getDiscoveryMovies()
            }

            override fun shouldFetch(data: List<FilmEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): ApiResponse<List<FilmResponse>>? {
                return remote.getDiscoveryMovie(page)
            }

            override suspend fun saveCallResult(data: List<FilmResponse>) {
                val filmEntities = mutableListOf<FilmEntity>()

                for (fr in data) {
                    filmEntities.add(
                        FilmEntity.toFilmEntity(fr).apply {
                            type = 1
                        }
                    )
                }

                local.insertFilm(filmEntities)
            }
        }.build().asLiveData()
    }

    override suspend fun getDiscoveryTv(page: Int): LiveData<Resource<List<FilmEntity>>> {
        return object : NetworkBoundResource<List<FilmEntity>, List<FilmResponse>>() {
            override suspend fun loadFromDB(): List<FilmEntity>? {
                return local.getDiscoveryTvs()
            }

            override fun shouldFetch(data: List<FilmEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): ApiResponse<List<FilmResponse>>? {
                return remote.getDiscoveryTv(page)
            }

            override suspend fun saveCallResult(data: List<FilmResponse>) {
                val filmEntities = mutableListOf<FilmEntity>()

                for (fr in data) {
                    filmEntities.add(
                        FilmEntity.toFilmEntity(fr).apply {
                            type = 2
                        }
                    )
                }

                local.insertFilm(filmEntities)
            }
        }.build().asLiveData()
    }

    override suspend fun getTv(tvId: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, TvResponse>() {
            override suspend fun loadFromDB(): TvEntity? {
                return local.getTv(tvId)
            }

            override fun shouldFetch(data: TvEntity?): Boolean {
                return data == null
            }

            override suspend fun createCall(): ApiResponse<TvResponse>? {
                return remote.getTv(tvId)
            }

            override suspend fun saveCallResult(data: TvResponse) {
                local.insertTv(TvEntity.toTv(data) as TvEntity)
            }
        }.build().asLiveData()
    }

    override suspend fun getMovie(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>() {
            override suspend fun loadFromDB(): MovieEntity? {
                return local.getMovie(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data == null
            }

            override suspend fun createCall(): ApiResponse<MovieResponse>? {
                return remote.getMovie(movieId)
            }

            override suspend fun saveCallResult(data: MovieResponse) {
                local.insertMovie(MovieEntity.toMovie(data) as MovieEntity)
            }
        }.build().asLiveData()
    }

    override suspend fun addFavorite(favoriteEntity: FavoriteEntity) {
        local.insertFavorite(favoriteEntity)
    }

    override suspend fun deleteFavoriteMovie(filmId: Int) {
        local.deleteFavoriteMovie(filmId)
    }

    override suspend fun deleteFavoriteTv(filmId: Int) {
        local.deleteFavoriteTv(filmId)
    }

    override suspend fun getFavorite(filmId: Int, type: Int): FavoriteEntity {
        return local.getFav(filmId, type)
    }

    override suspend fun getFavoriteFilmPaged(type: Int): LiveData<Resource<PagedList<FavoriteEntity>>> {
        return object : NetworkBoundResource<PagedList<FavoriteEntity>, List<FavoriteEntity>>() {
            override suspend fun loadFromDB(): PagedList<FavoriteEntity>? {
                return LivePagedListBuilder(local.getFavoriteMovie(), 20).build().value
            }

            override fun shouldFetch(data: PagedList<FavoriteEntity>?): Boolean {
                return false
            }

            override suspend fun createCall(): ApiResponse<List<FavoriteEntity>>? {
                return null
            }

            override suspend fun saveCallResult(data: List<FavoriteEntity>) {

            }
        }.build().asLiveData()
    }

    companion object {

        @Volatile
        private var INSTANCE: MovieCatalogRepository? = null

        fun getInstance(
            localData: LocalRepository,
            remoteData: RemoteRepository
        ): MovieCatalogRepository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = MovieCatalogRepository(localData, remoteData)
                INSTANCE = instance
                return instance
            }
        }
    }
}