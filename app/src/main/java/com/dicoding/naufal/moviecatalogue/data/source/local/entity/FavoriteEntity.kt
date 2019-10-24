package com.dicoding.naufal.moviecatalogue.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "filmId")
    @NonNull
    var filmId: Int = 0,

    @ColumnInfo(name = "type")
    @NonNull
    var type: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double = 0.0,

    @ColumnInfo(name = "posterPath")
    var posterPath: String? = null
)