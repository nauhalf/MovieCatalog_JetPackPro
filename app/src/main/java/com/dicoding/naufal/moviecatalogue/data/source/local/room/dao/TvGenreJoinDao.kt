package com.dicoding.naufal.moviecatalogue.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.GenreEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.join.TvGenreJoin

@Dao
interface TvGenreJoinDao {
    @Query("""
        SELECT * FROM genres 
        INNER JOIN tv_genre_join 
        ON genres.id = tv_genre_join.genreId
        WHERE tv_genre_join.tvId = :tvId
    """)
    fun getGenreWithTvId(tvId: Int) : List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(join: TvGenreJoin)
}