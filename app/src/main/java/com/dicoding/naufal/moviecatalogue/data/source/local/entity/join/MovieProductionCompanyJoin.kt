package com.dicoding.naufal.moviecatalogue.data.source.local.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.GenreEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.ProductionCompanyEntity

@Entity(tableName = "movie_production_company_join",
    primaryKeys = ["movieId", "productionCompanyId"],
    indices = [
        Index(value = ["movieId"]),
        Index(value = ["productionCompanyId"])
    ],
    foreignKeys = [
        ForeignKey(entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"]),
        ForeignKey(entity = ProductionCompanyEntity::class,
            parentColumns = ["id"],
            childColumns = ["productionCompanyId"])
    ])
data class MovieProductionCompanyJoin(

    @ColumnInfo(name = "movieId")
    val movieId: Int,

    @ColumnInfo(name = "productionCompanyId")
    val productionCompanyId: Int
)