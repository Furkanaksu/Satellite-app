package com.furkan.satellite_app.features.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkan.satellite_app.data.model.SatelliteUIModel
import com.furkan.satellite_app.domain.usecase.list.SatelliteListUseCase
import com.furkan.satellite_app.domain.usecase.list.SatelliteSearchUseCase
import com.furkan.satellite_app.utils.Constant.SATELLITE_FILE
import com.furkan.satellite_app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteListViewModel @Inject constructor(
    private val satelliteListUseCase: SatelliteListUseCase,
    private val satelliteListSearchUseCase: SatelliteSearchUseCase
) : ViewModel() {

    private val _listFlow = MutableStateFlow<SatelliteListViewState?>(null)
    val listFlow: StateFlow<SatelliteListViewState?> get() = _listFlow

    fun getSatelliteList() {
        _listFlow.value = SatelliteListViewState.Loading
        viewModelScope.launch {
            satelliteListUseCase.invoke(SatelliteListUseCase.SatelliteListParams(SATELLITE_FILE)).collect { state->
                when (state) {
                    is Resource.Success -> {
                        _listFlow.emit(SatelliteListViewState.Success(state.data))
                    }
                    is Resource.Failure -> {
                        _listFlow.emit(SatelliteListViewState.Failure(state.error))
                    }
                    is Resource.Loading -> {
                        _listFlow.emit(SatelliteListViewState.Loading)
                    }
                }
            }
        }
    }

    fun makeSearch(searchKey: String) {
        _listFlow.value = SatelliteListViewState.Loading
        viewModelScope.launch {
            satelliteListSearchUseCase.invoke(SatelliteSearchUseCase.SatelliteSearchParams(SATELLITE_FILE, searchKey)).collect{state->
                when (state) {
                    is Resource.Success -> {
                        _listFlow.emit(SatelliteListViewState.Success(state.data))

                    }
                    is Resource.Failure -> {
                        _listFlow.emit(SatelliteListViewState.Failure(state.error))
                    }
                    is Resource.Loading -> {
                        _listFlow.emit(SatelliteListViewState.Loading)
                    }
                }
            }
        }
    }
}


sealed class SatelliteListViewState {
    object Loading : SatelliteListViewState()
    class Success(val list: List<SatelliteUIModel?>?) : SatelliteListViewState()
    class Failure(val errorName: String) : SatelliteListViewState()
}

