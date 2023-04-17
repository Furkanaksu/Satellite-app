package com.furkan.satellite_app.data.repository


import com.furkan.satellite_app.data.local.entity.SatelliteDetail
import com.furkan.satellite_app.data.local.entity.SatellitePosition
import com.furkan.satellite_app.data.local.source.RoomDataSource
import com.furkan.satellite_app.data.local.source.file.FileDataSource
import com.furkan.satellite_app.data.model.Satellite
import com.furkan.satellite_app.domain.repository.SatelliteRepository
import com.furkan.satellite_app.utils.Constant.SATELLITE_DETAIL_FILE
import com.furkan.satellite_app.utils.Resource
import javax.inject.Inject

class SatelliteRepositoryImpl @Inject constructor(
    private val fileDataSource: FileDataSource,
    private val roomDataSource: RoomDataSource
) : SatelliteRepository {

    override suspend fun getSatellites(fileName: String): Resource<List<Satellite>?> {
        return fileDataSource.getSatelliteList(fileName)
    }

    override suspend fun getSatelliteDetail(
        fileName: String,
        satelliteId: Int
    ): Resource<List<SatelliteDetail?>?> {
        return when (val satelliteDetails = roomDataSource.getSatelliteDetails()) {
            is Resource.Success -> {
                if (satelliteDetails.data?.any { it.id == satelliteId } == true) {
                    satelliteDetails
                } else {
                    fileDataSource.getSatelliteDetail(SATELLITE_DETAIL_FILE)
                }
            }
            is Resource.Failure -> fileDataSource.getSatelliteDetail(SATELLITE_DETAIL_FILE)
            is Resource.Loading -> fileDataSource.getSatelliteDetail(SATELLITE_DETAIL_FILE)
        }
    }

    override suspend fun getSatellitePositions(
        fileName: String,
        satelliteId: Int
    ): Resource<List<SatellitePosition?>?> {
        return when (val satellitePositions = roomDataSource.getSatellitePositions()) {
            is Resource.Success -> {
                if (satellitePositions.data?.any { it.id == satelliteId } == true) {
                    satellitePositions
                } else {
                    when (val listObject = fileDataSource.getSatellitePosition(fileName)) {
                        is Resource.Success -> Resource.Success(listObject.data?.list)
                        is Resource.Failure -> Resource.Failure(listObject.error)
                        is Resource.Loading -> Resource.Loading()
                    }
                }
            }
            is Resource.Failure -> when (val listObject =
                fileDataSource.getSatellitePosition(fileName)) {
                is Resource.Success -> Resource.Success(listObject.data?.list)
                is Resource.Failure -> Resource.Failure(listObject.error)
                is Resource.Loading -> Resource.Loading()
            }
            is Resource.Loading -> when (val listObject =
                fileDataSource.getSatellitePosition(fileName)) {
                is Resource.Success -> Resource.Success(listObject.data?.list)
                is Resource.Failure -> Resource.Failure(listObject.error)
                is Resource.Loading -> Resource.Loading()
            }
        }
    }

    override suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail) {
        roomDataSource.deleteSatelliteDetail(satelliteDetail)
        roomDataSource.insertSatelliteDetail(satelliteDetail)
    }

    override suspend fun insertSatellitePosition(satellitePosition: SatellitePosition) {
        roomDataSource.deleteSatellitePosition(satellitePosition)
        roomDataSource.insertSatellitePosition(satellitePosition)
    }
}