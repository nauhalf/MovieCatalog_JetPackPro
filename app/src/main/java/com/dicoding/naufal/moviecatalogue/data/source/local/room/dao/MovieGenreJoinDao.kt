package com.dicoding.naufal.moviecatalogue.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.GenreEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.join.MovieGenreJoin

@Dao
interface MovieGenreJoinDao {
    @Query("""
        SELECT * FROM genres 
        INNER JOIN movie_genre_join 
        ON genres.id = movie_genre_join.genreId
        WHERE movie_genre_join.movieId = :movieId
    """)
    fun getGenreWithMovieId(movieId: Int) : List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(join: MovieGenreJoin)
}