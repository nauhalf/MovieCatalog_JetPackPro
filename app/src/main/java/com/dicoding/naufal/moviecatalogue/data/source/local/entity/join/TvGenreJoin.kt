package com.dicoding.naufal.moviecatalogue.data.source.local.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.GenreEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.TvEntity

@Entity(tableName = "tv_genre_join",
    primaryKeys = ["tvId", "genreId"],
    indices = [
        Index(value = ["tvId"]),
        Index(value = ["genreId"])
    ],
    foreignKeys = [
        ForeignKey(entity = TvEntity::class,
            parentColumns = ["id"],
            childColumns = ["tvId"]),
        ForeignKey(entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"])
    ])
data class TvGenreJoin(
    @ColumnInfo(name = "tvId")
    val tvId: Int,

    @ColumnInfo(name = "genreId")
    val genreId: Int
)