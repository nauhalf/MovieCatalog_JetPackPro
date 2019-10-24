package com.dicoding.naufal.moviecatalogue.data.source.local.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.GenreEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.MovieEntity

@Entity(
    tableName = "movie_genre_join",
    primaryKeys = ["movieId", "genreId"],
    indices = [
        Index(value = ["movieId"]),
        Index(value = ["genreId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"]
        ),
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"]
        )
    ]
)
data class MovieGenreJoin(
    @ColumnInfo(name = "movieId")
    val movieId: Int,

    @ColumnInfo(name = "genreId")
    val genreId: Int
)