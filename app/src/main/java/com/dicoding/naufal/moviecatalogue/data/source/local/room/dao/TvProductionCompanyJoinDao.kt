package com.dicoding.naufal.moviecatalogue.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.ProductionCompanyEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.join.TvProductionCompanyJoin

@Dao
interface TvProductionCompanyJoinDao {
    @Query("""
        SELECT * FROM productionCompanies 
        INNER JOIN tv_production_company_join 
        ON productionCompanies.id = tv_production_company_join.productionCompanyId
        WHERE tv_production_company_join.tvId = :tvId
    """)
    fun getProductionCompanyWithTvId(tvId: Int) : List<ProductionCompanyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(join: TvProductionCompanyJoin)
}