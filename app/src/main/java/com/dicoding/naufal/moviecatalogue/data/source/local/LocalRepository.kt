package com.dicoding.naufal.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.*
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.join.*
import com.dicoding.naufal.moviecatalogue.data.source.local.room.MovieCatalogDatabase

class LocalRepository(private val movieDB: MovieCatalogDatabase) {

    fun getDiscoveryMovies(): List<FilmEntity> {
        return movieDB.movieCatalogDao().getDiscoveryMovies()
    }

    fun getDiscoveryTvs(): List<FilmEntity> {
        return movieDB.movieCatalogDao().getDiscoveryTvs()
    }

    fun getMovie(movieId: Int): MovieEntity? {

        val movie: MovieEntity? = movieDB.movieCatalogDao().getMovie(movieId)
        val listGenre = movieDB.movieGenreJoinDao().getGenreWithMovieId(movieId)
        val listProductionCompany =
            movieDB.movieProductionCompanyJoinDao().getProductionCompanyWithMovieId(movieId)
        movie?.genres = listGenre
        movie?.productionCompanies = listProductionCompany
        return movie
    }

    fun getTv(tvId: Int): TvEntity? {

        val tv : TvEntity? = movieDB.movieCatalogDao().getTv(tvId)
        val listGenre = movieDB.tvGenreJoinDao().getGenreWithTvId(tvId)
        val listProductionCompany =
            movieDB.tvProductionCompanyJoinDao().getProductionCompanyWithTvId(tvId)
        val listCreator = movieDB.tvCreatorJoinDao().getCreatorWithTvId(tvId)
        tv?.genres = listGenre
        tv?.productionCompanies = listProductionCompany
        tv?.creators = listCreator
        return tv
    }

    fun getFavoriteMovie(): DataSource.Factory<Int, FavoriteEntity> {
        return movieDB.movieCatalogDao().getFavoriteMovieAsPaged()
    }

    fun getFavoriteTv(): DataSource.Factory<Int, FavoriteEntity> {
        return movieDB.movieCatalogDao().getFavoriteTvAsPaged()
    }

    fun insertFilm(movies: List<FilmEntity>): LongArray {
        return movieDB.movieCatalogDao().insertFilm(movies)
    }

    fun insertMovie(movie: MovieEntity): Long {
        val genres = movie.genres
        val productionCompanies = movie.productionCompanies
        genres?.let {
            movieDB.movieCatalogDao().insertGenres(it)
        }

        productionCompanies?.let {
            movieDB.movieCatalogDao().insertProductionCompanies(it)
        }

        val i = movieDB.movieCatalogDao().insertMovie(movie)

        genres?.forEach { genre ->
            movieDB.movieGenreJoinDao().insert(MovieGenreJoin(movie.id, genre.id))
        }

        productionCompanies?.forEach { prodComp ->
            movieDB.movieProductionCompanyJoinDao().insert(
                MovieProductionCompanyJoin(movie.id, prodComp.id)
            )
        }


        return i
    }

    fun insertTv(tv: TvEntity): Long {
        val genres = tv.genres
        val productionCompanies = tv.productionCompanies
        val creators = tv.creators

        genres?.let {
            movieDB.movieCatalogDao().insertGenres(it)
        }

        productionCompanies?.let {
            movieDB.movieCatalogDao().insertProductionCompanies(it)
        }

        creators?.let {
            movieDB.movieCatalogDao().insertCreators(it)
        }

        val i = movieDB.movieCatalogDao().insertTv(tv)

        genres?.forEach { genre ->
            movieDB.tvGenreJoinDao().insert(TvGenreJoin(tv.id, genre.id))
        }

        productionCompanies?.forEach { prodComp ->
            movieDB.tvProductionCompanyJoinDao().insert(
                TvProductionCompanyJoin(tv.id, prodComp.id)
            )
        }

        creators?.forEach { creator ->
            movieDB.tvCreatorJoinDao().insert(
                TvCreatorJoin(tv.id, creator.id)
            )
        }

        return i
    }

    fun insertFavorite(fav: FavoriteEntity): Long {
        return movieDB.movieCatalogDao().insertFavorite(fav)
    }

    fun deleteFavoriteMovie(filmId: Int) {
        movieDB.movieCatalogDao().deleteFavoriteMovie(filmId)
    }

    fun deleteFavoriteTv(filmId: Int) {
        movieDB.movieCatalogDao().deleteFavoriteTv(filmId)
    }

    fun getFav(filmId: Int, type: Int): FavoriteEntity{
        return movieDB.movieCatalogDao().getFavFilm(filmId, type)
    }

    companion object {

        @Volatile
        private var INSTANCE: LocalRepository? = null

        fun getInstance(db: MovieCatalogDatabase): LocalRepository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = LocalRepository(db)
                INSTANCE = instance
                return instance
            }
        }
    }
}