package com.furkan.satellite_app.domain.usecase.detail

import com.furkan.satellite_app.data.local.entity.SatellitePosition
import com.furkan.satellite_app.domain.repository.SatelliteRepository
import com.furkan.satellite_app.domain.usecase.BaseUseCase
import com.furkan.satellite_app.utils.Resource
import javax.inject.Inject

class InsertSatellitePositionUseCase @Inject constructor(
    private val repository: SatelliteRepository
) : BaseUseCase<InsertSatellitePositionUseCase.Request, Unit>() {
    override suspend fun execute(request: Request): Resource<Unit> {
        return try {
            request.satellitePosition?.let { repository.insertSatellitePosition(it) }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Request(val satellitePosition: SatellitePosition?)
}