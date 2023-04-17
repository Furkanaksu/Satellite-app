package com.furkan.satellite_app.domain.usecase.detail

import com.furkan.satellite_app.data.local.entity.SatelliteDetail
import com.furkan.satellite_app.domain.repository.SatelliteRepository
import com.furkan.satellite_app.domain.usecase.BaseUseCase
import com.furkan.satellite_app.utils.Constant.SATELLITE_DETAIL_FILE
import com.furkan.satellite_app.utils.Resource
import javax.inject.Inject

class GetSatelliteDetailUseCase @Inject constructor(
    private val repository: SatelliteRepository,
    private val insertSatelliteDetailUseCase: InsertSatelliteDetailUseCase
) : BaseUseCase<GetSatelliteDetailUseCase.Request, List<SatelliteDetail?>?>() {

    override suspend fun execute(request: Request): Resource<List<SatelliteDetail?>?> {
        return try {
            when (val satelliteDetailList =
                repository.getSatelliteDetail(SATELLITE_DETAIL_FILE, request.satelliteId)) {
                is Resource.Success -> {
                    insertSatelliteDetailUseCase.execute(
                        InsertSatelliteDetailUseCase.Request(
                            satelliteDetailList.data?.find { it?.id == request.satelliteId })
                    )
                    Resource.Success(satelliteDetailList.data)
                }
                is Resource.Failure -> Resource.Failure(satelliteDetailList.error)
                is Resource.Loading -> Resource.Loading()
            }
        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Request(val satelliteId: Int)

}