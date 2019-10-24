package com.dicoding.naufal.moviecatalogue.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.ProductionCompanyEntity
import com.dicoding.naufal.moviecatalogue.data.source.local.entity.join.MovieProductionCompanyJoin

@Dao
interface MovieProductionCompanyJoinDao {
    @Query(
        """
        SELECT * FROM productionCompanies 
        INNER JOIN movie_production_company_join 
        ON productionCompanies.id = movie_production_company_join.productionCompanyId
        WHERE movie_production_company_join.movieId = :movieId
    """
    )
    fun getProductionCompanyWithMovieId(movieId: Int): List<ProductionCompanyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(join: MovieProductionCompanyJoin)
}