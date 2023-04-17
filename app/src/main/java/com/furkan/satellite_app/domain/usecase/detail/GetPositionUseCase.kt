package com.furkan.satellite_app.domain.usecase.detail


import com.furkan.satellite_app.data.local.entity.SatellitePosition
import com.furkan.satellite_app.domain.repository.SatelliteRepository
import com.furkan.satellite_app.domain.usecase.BaseUseCase
import com.furkan.satellite_app.utils.Constant.SATELLITE_POSITION_FILE
import com.furkan.satellite_app.utils.Resource
import javax.inject.Inject

class GetPositionUseCase @Inject constructor(
    private val repository: SatelliteRepository,
    private val insertSatellitePositionUseCase: InsertSatellitePositionUseCase
) : BaseUseCase<GetPositionUseCase.Request, List<SatellitePosition?>?>() {
    override suspend fun execute(request: Request): Resource<List<SatellitePosition?>?> {
        return try {
            when (val positions =
                repository.getSatellitePositions(SATELLITE_POSITION_FILE, request.satelliteId)) {
                is Resource.Success -> {
                    insertSatellitePositionUseCase.execute(
                        InsertSatellitePositionUseCase.Request(
                            positions.data?.find { it?.id == request.satelliteId })
                    )
                    Resource.Success(positions.data)
                }
                is Resource.Failure -> Resource.Failure(error = positions.error)
                is Resource.Loading -> Resource.Loading()
            }
        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Request(val satelliteId: Int)
}