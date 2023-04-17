package com.furkan.satellite_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.furkan.satellite_app.data.local.entity.SatelliteDetail
import com.furkan.satellite_app.data.local.entity.SatellitePosition

@Dao
interface SatelliteDao {

    @Query("SELECT * FROM satellite_detail")
    fun getSatelliteDetails(): List<SatelliteDetail>?

    @Query("SELECT * FROM  satellite_position")
    fun getSatellitePositions(): List<SatellitePosition>?

    @Insert
    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)

    @Insert
    suspend fun insertSatellitePosition(satellitePosition: SatellitePosition)

    @Delete
    suspend fun deleteSatelliteDetail(satelliteDetail: SatelliteDetail)

    @Delete
    suspend fun deleteSatellitePosition(satellitePosition: SatellitePosition)
}