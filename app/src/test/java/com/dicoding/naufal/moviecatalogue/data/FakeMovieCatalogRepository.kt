package com.dicoding.naufal.moviecatalogue.data

import com.dicoding.naufal.moviecatalogue.data.source.local.entity.FilmEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.TvEntity
import com.dicoding.naufal.moviecatalogue.data.source.remote.RemoteRepository

class FakeMovieCatalogRepository(val remote: RemoteRepository) : MovieCatalogDataSource {
    override suspend fun getDiscoveryMovie(page: Int): List<FilmEntity> {
        val listFilm = mutableListOf<FilmEntity>()
        val response = remote.getDiscoveryMovie(page)

        response?.forEach { f ->
            val film = FilmEntity.toFilmEntity(f)
            listFilm.add(film)
        }

        return listFilm
    }

    override suspend fun getDiscoveryTv(page: Int): List<FilmEntity> {
        val listFilm = mutableListOf<FilmEntity>()
        val response = remote.getDiscoveryTv(page)

        response?.forEach { f ->
            val film = FilmEntity.toFilmEntity(f)
            listFilm.add(film)
        }


        return listFilm
    }

    override suspend fun getTv(tvId: Int): TvEntity? {
        val response = remote.getTv(tvId)

        return TvEntity.toTv(response)
    }

    override suspend fun getMovie(movieId: Int): MovieEntity? {
        val response = remote.getMovie(movieId)
        return MovieEntity.toMovie(response)
    }

    companion object {

        @Volatile
        private var INSTANCE: FakeMovieCatalogRepository? = null

        fun getInstance(remoteData: RemoteRepository): FakeMovieCatalogRepository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = FakeMovieCatalogRepository(remoteData)
                INSTANCE = instance
                return instance
            }
        }
    }
}