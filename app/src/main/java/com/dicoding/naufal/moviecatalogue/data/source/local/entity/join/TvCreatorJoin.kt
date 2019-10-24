package com.dicoding.naufal.moviecatalogue.data.source.local.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.CreatorEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.TvEntity

@Entity(
    tableName = "tv_creator_join",
    primaryKeys = ["tvId", "creatorId"],
    indices = [
        Index(value = ["tvId"]),
        Index(value = ["creatorId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = TvEntity::class,
            parentColumns = ["id"],
            childColumns = ["tvId"]
        ),
        ForeignKey(
            entity = CreatorEntity::class,
            parentColumns = ["id"],
            childColumns = ["creatorId"]
        )
    ]
)
data class TvCreatorJoin(
    @ColumnInfo(name = "tvId")
    val tvId: Int,
    @ColumnInfo(name = "creatorId")
    val creatorId: Int
)