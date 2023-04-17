package com.furkan.satellite_app.domain.usecase.list

import com.furkan.satellite_app.data.model.SatelliteUIModel
import com.furkan.satellite_app.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SatelliteSearchUseCase @Inject constructor(
    private val satelliteListUseCase: SatelliteListUseCase,
){
    suspend operator fun invoke(request: SatelliteSearchParams): Flow<Resource<List<SatelliteUIModel?>?>> {
        return flow {
            emit(Resource.Loading())
            delay(500)
            satelliteListUseCase.invoke(SatelliteListUseCase.SatelliteListParams(request.fileName)).collect{response->
                try {
                    when (response) {
                        is Resource.Success -> Resource.Success(
                            emit(
                                Resource.Success(
                                    response.data?.filter {
                                        it?.name?.lowercase()?.contains(request.searchKey.lowercase()) == true
                                    }
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
    }
    data class SatelliteSearchParams(val fileName: String, val searchKey: String)
}