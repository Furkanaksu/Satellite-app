package com.furkan.satellite_app.data.local.source.file

import com.furkan.satellite_app.data.local.entity.SatelliteDetail
import com.furkan.satellite_app.data.model.Satellite
import com.furkan.satellite_app.data.model.SatellitePositionsList
import com.furkan.satellite_app.utils.Resource

interface FileDataSource {
    suspend fun getSatelliteList(fileName: String): Resource<List<Satellite>?>
    suspend fun getSatelliteDetail(fileName: String): Resource<List<SatelliteDetail>?>
    suspend fun getSatellitePosition(fileName: String): Resource<SatellitePositionsList?>
}