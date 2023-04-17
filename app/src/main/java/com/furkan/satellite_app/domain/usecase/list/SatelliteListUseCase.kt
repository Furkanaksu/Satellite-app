package com.furkan.satellite_app.domain.usecase.list

import com.furkan.satellite_app.data.model.SatelliteUIModel
import com.furkan.satellite_app.domain.mapper.SatelliteUIModelMapper
import com.furkan.satellite_app.domain.repository.SatelliteRepository
import com.furkan.satellite_app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SatelliteListUseCase @Inject constructor(
    private val repository: SatelliteRepository
){
    suspend operator fun invoke(request: SatelliteListParams): Flow<Resource<List<SatelliteUIModel?>?>> {
        return flow {
            emit(Resource.Loading())
            val response = repository.getSatellites(request.fileName)
            try {
                when (response) {
                    is Resource.Success -> Resource.Success(
                        emit(
                            Resource.Success(
                                SatelliteUIModelMapper
                                    .satelliteFileResponseToUIModel(response.data)
                            )
                        )
                    )
                    is Resource.Failure -> Resource.Failure(response.error)
                    is Resource.Loading -> Resource.Loading()
                }
            } catch (e: Exception) {
                emit(Resource.Failure("Error"))
            }
        }
    }
    data class SatelliteListParams(val fileName: String)
}
