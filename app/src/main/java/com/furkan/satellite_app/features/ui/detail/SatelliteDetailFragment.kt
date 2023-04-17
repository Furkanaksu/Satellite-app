package com.furkan.satellite_app.features.ui.detail

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.furkan.satellite_app.R
import com.furkan.satellite_app.data.model.PositionCoordinate
import com.furkan.satellite_app.data.model.SatelliteDetailUIModel
import com.furkan.satellite_app.databinding.FragmentSatelliteDetailBinding
import com.furkan.satellite_app.features.base.BaseFragment
import com.furkan.satellite_app.utils.helper.partialBold
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SatelliteDetailFragment : BaseFragment<FragmentSatelliteDetailBinding, SatelliteDetailViewModel>() {


    override val viewModel: SatelliteDetailViewModel by viewModels()
    private val args: SatelliteDetailFragmentArgs by navArgs()


    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSatelliteDetailBinding {
        return FragmentSatelliteDetailBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        viewModel.getSatelliteDetail(args.id)
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.satelliteDetailFlow.collect {
                    it?.let {
                        when (it) {
                            SatelliteDetailViewState.Loading -> {
                                showLoading()
                            }
                            is SatelliteDetailViewState.DataLoaded -> {
                                updateUI(it.satelliteDetailUIModel)
                                hideLoading()
                            }
                            is SatelliteDetailViewState.PositionChange -> {
                                updatePosition(it.position)
                                hideLoading()
                            }
                            is SatelliteDetailViewState.Failure -> {
                                Toast.makeText(
                                    context,
                                    it.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                                hideLoading()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updatePosition(it: PositionCoordinate?){
        binding?.lastPositionTv?.partialBold(
            boldString = resources.getString(R.string.last_position),
            regularText = "(".plus(
                it?.posX.toString()
                    .plus(it?.posY.toString())
            ).plus(")")
        )
    }

    private fun updateUI(data: SatelliteDetailUIModel?) {
        binding?.apply{
            satelliteNameTv.text = args.name
            dateTv.text = data?.dateText
            heightMassTv.partialBold(
                resources.getString(R.string.height_mass),
                data?.heightMassText
            )
            costTv.partialBold(
                boldString = resources.getString(R.string.cost),
                data?.costText
            )
            lastPositionTv.partialBold(
                boldString = resources.getString(R.string.last_position),
                regularText = ("(").plus(
                    data?.lastPosition?.get(0)?.positions?.get(0)?.posX.toString()
                        .plus(data?.lastPosition?.get(0)?.positions?.get(0)?.posY.toString())
                        .plus(")")
                )
            )
        }
    }
}