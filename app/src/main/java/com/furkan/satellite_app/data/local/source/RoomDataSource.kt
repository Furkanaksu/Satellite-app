package com.furkan.satellite_app.data.local.source

import com.furkan.satellite_app.data.local.entity.SatelliteDetail
import com.furkan.satellite_app.data.local.entity.SatellitePosition
import com.furkan.satellite_app.utils.Resource


interface RoomDataSource {
    suspend fun getSatelliteDetails(): Resource<List<SatelliteDetail>?>
    suspend fun getSatellitePositions(): Resource<List<SatellitePosition>?>
    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)
    suspend fun insertSatellitePosition(satellitePosition: SatellitePosition)
    suspend fun deleteSatelliteDetail(satelliteDetail: SatelliteDetail)
    suspend fun deleteSatellitePosition(satellitePosition: SatellitePosition)
}