package com.dicoding.naufal.moviecatalogue.data.source.local.room.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.*

@Dao
interface MovieCatalogDao {

    @Query("SELECT * FROM films WHERE films.type = 1")
    fun getDiscoveryMovies(): List<FilmEntity>

    @Query("SELECT * FROM films WHERE films.type = 2")
    fun getDiscoveryTvs(): List<FilmEntity>

    @Query("SELECT * FROM movies WHERE movies.id = :movieId")
    fun getMovie(movieId: Int): MovieEntity

    @Query("SELECT * FROM tvs WHERE tvs.id = :tvId")
    fun getTv(tvId: Int): TvEntity

    @Query("SELECT * FROM favorites WHERE favorites.type = 1 ORDER BY favorites.id DESC")
    fun getFavoriteMovieAsPaged(): DataSource.Factory<Int, FavoriteEntity>

    @Query("SELECT * FROM favorites WHERE favorites.type = 2 ORDER BY favorites.id DESC")
    fun getFavoriteTvAsPaged(): DataSource.Factory<Int, FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(movies: List<FilmEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(tv: TvEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCreators(creators: List<CreatorEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<GenreEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductionCompanies(productionCompanies: List<ProductionCompanyEntity>): LongArray

    @Insert
    fun insertFavorite(fav: FavoriteEntity): Long

    @Query("DELETE FROM favorites WHERE favorites.filmId = :filmId AND favorites.type = 1")
    fun deleteFavoriteMovie(filmId: Int)

    @Query("DELETE FROM favorites WHERE favorites.filmId = :filmId AND favorites.type = 2")
    fun deleteFavoriteTv(filmId: Int)

    @Query("SELECT * FROM favorites WHERE favorites.filmId = :filmId AND favorites.type = :type LIMIT 1")
    fun getFavFilm(filmId: Int?, type: Int): FavoriteEntity

}