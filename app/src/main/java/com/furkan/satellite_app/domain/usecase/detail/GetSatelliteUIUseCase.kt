package com.furkan.satellite_app.domain.usecase.detail


import com.furkan.satellite_app.data.model.SatelliteDetailUIModel
import com.furkan.satellite_app.domain.mapper.SatelliteDetailUIModelMapper
import com.furkan.satellite_app.domain.usecase.BaseUseCase
import com.furkan.satellite_app.utils.Resource
import javax.inject.Inject

class GetSatelliteUIUseCase @Inject constructor(
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val getPositionUseCase: GetPositionUseCase
) : BaseUseCase<GetSatelliteUIUseCase.Request, SatelliteDetailUIModel?>() {
    override suspend fun execute(request: Request): Resource<SatelliteDetailUIModel?> {
        return try {
            when (val satelliteDetail =
                getSatelliteDetailUseCase.execute(GetSatelliteDetailUseCase.Request(request.satelliteId))) {
                is Resource.Success -> {
                    when (val positions =
                        getPositionUseCase.execute(GetPositionUseCase.Request(request.satelliteId))) {
                        is Resource.Success -> {
                            Resource.Success(
                                SatelliteDetailUIModelMapper.satelliteDetailToUIModel(
                                    satelliteDetail.data,
                                    positions.data
                                )?.find { it?.satelliteId == request.satelliteId }
                            )
                        }
                        is Resource.Failure -> Resource.Failure(positions.error)
                        is Resource.Loading -> Resource.Loading()
                    }
                }
                is Resource.Failure -> Resource.Failure(satelliteDetail.error)
                is Resource.Loading -> Resource.Loading()
            }

        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Request(val satelliteId: Int)
}