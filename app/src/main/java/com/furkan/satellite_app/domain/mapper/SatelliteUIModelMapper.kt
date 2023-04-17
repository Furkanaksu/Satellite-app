package com.furkan.satellite_app.domain.mapper

import com.furkan.satellite_app.R
import com.furkan.satellite_app.data.model.Satellite
import com.furkan.satellite_app.data.model.SatelliteUIModel

object SatelliteUIModelMapper {

    fun satelliteFileResponseToUIModel(list: List<Satellite?>?): List<SatelliteUIModel?>? {
        return list?.map {
            SatelliteUIModel(
                satelliteId = it?.id,
                name = it?.name,
                activeText = if (it?.active == true) "Active" else "Passive",
                activeImg = if (it?.active == true) (R.drawable.ic_green_circle) else (R.drawable.ic_red_circle),
                active = it?.active
            )
        }
    }
}