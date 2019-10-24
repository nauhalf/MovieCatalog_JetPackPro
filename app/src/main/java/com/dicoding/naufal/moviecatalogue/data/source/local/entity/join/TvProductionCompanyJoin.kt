package com.dicoding.naufal.moviecatalogue.data.source.local.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.ProductionCompanyEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.TvEntity

@Entity(tableName = "tv_production_company_join",
    primaryKeys = ["tvId", "productionCompanyId"],
    indices = [
        Index(value = ["tvId"]),
        Index(value = ["productionCompanyId"])
    ],
    foreignKeys = [
        ForeignKey(entity = TvEntity::class,
            parentColumns = ["id"],
            childColumns = ["tvId"]),
        ForeignKey(entity = ProductionCompanyEntity::class,
            parentColumns = ["id"],
            childColumns = ["productionCompanyId"])
    ])
data class TvProductionCompanyJoin(
    @ColumnInfo(name = "tvId")
    val tvId: Int,

    @ColumnInfo(name = "productionCompanyId")
    val productionCompanyId: Int
)