package com.furkan.satellite_app.features.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkan.satellite_app.data.local.entity.SatellitePosition
import com.furkan.satellite_app.data.model.PositionCoordinate
import com.furkan.satellite_app.data.model.SatelliteDetailUIModel
import com.furkan.satellite_app.domain.usecase.detail.GetSatelliteUIUseCase
import com.furkan.satellite_app.utils.Constant.POSITION_CHANGE_DELAY
import com.furkan.satellite_app.utils.Constant.SEARCH_DELAY
import com.furkan.satellite_app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val getSatelliteUIUseCase: GetSatelliteUIUseCase
) : ViewModel() {

    private val _satelliteDetailFlow =
        MutableStateFlow<SatelliteDetailViewState?>(null)
    val satelliteDetailFlow: StateFlow<SatelliteDetailViewState?> get() = _satelliteDetailFlow

    fun getSatelliteDetail(satelliteId: Int) {
        _satelliteDetailFlow.value = SatelliteDetailViewState.Loading
        viewModelScope.launch {
            delay(SEARCH_DELAY)
            getSatelliteUIUseCase.execute(GetSatelliteUIUseCase.Request(satelliteId)).let {
                when (it) {
                    is Resource.Success -> {
                        _satelliteDetailFlow.emit(SatelliteDetailViewState.DataLoaded(it.data))
                        it.data?.lastPosition?.get(0)?.positions?.forEach {
                            _satelliteDetailFlow.emit(SatelliteDetailViewState.PositionChange(it))
                            delay(POSITION_CHANGE_DELAY)
                        }
                    }
                    is Resource.Failure -> {
                        _satelliteDetailFlow.emit(SatelliteDetailViewState.Failure(it.error))
                    }
                    is Resource.Loading -> {
                        _satelliteDetailFlow.emit(SatelliteDetailViewState.Loading)
                    }
                }
            }
        }
    }
}

sealed class SatelliteDetailViewState {
    object Loading : SatelliteDetailViewState()
    class DataLoaded(val satelliteDetailUIModel: SatelliteDetailUIModel?) : SatelliteDetailViewState()
    class PositionChange(val position: PositionCoordinate?) : SatelliteDetailViewState()
    class Failure(val error: String?) : SatelliteDetailViewState()
}