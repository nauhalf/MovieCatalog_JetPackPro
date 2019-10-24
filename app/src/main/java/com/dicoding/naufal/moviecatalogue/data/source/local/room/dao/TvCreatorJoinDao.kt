package com.dicoding.naufal.moviecatalogue.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.CreatorEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.join.TvCreatorJoin

@Dao
interface TvCreatorJoinDao {
    @Query(
        """
        SELECT * FROM creators 
        INNER JOIN tv_creator_join 
        ON creators.id = tv_creator_join.creatorId
        WHERE tv_creator_join.tvId = :tvId
    """
    )
    fun getCreatorWithTvId(tvId: Int): List<CreatorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(join: TvCreatorJoin)
}