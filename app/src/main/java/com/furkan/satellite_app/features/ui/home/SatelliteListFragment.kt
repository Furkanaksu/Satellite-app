package com.furkan.satellite_app.features.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkan.satellite_app.R
import com.furkan.satellite_app.data.model.SatelliteUIModel
import com.furkan.satellite_app.databinding.FragmentSatelliteListBinding
import com.furkan.satellite_app.features.base.BaseFragment
import com.furkan.satellite_app.utils.Constant.SEARCH_DELAY
import com.furkan.satellite_app.utils.DividerItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SatelliteListFragment : BaseFragment<FragmentSatelliteListBinding, SatelliteListViewModel>() {

    override val viewModel: SatelliteListViewModel by viewModels()
    private lateinit var textChangeCountDownJob: Job
    private val listAdapter by lazy { SatelliteListAdapter(::onClicked) }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSatelliteListBinding {
        return FragmentSatelliteListBinding.inflate(inflater, container, false)
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listFlow.collect {
                    it?.let {
                        when (it) {
                            SatelliteListViewState.Loading -> {
                                showLoading()
                            }
                            is SatelliteListViewState.Success -> {
                                listAdapter.updateItemList(it.list?.toMutableList())
                                hideLoading()
                            }
                            is SatelliteListViewState.Failure -> {
                                Toast.makeText(context, it.errorName, Toast.LENGTH_SHORT).show()
                                hideLoading()
                            }
                        }
                    }
                }
            }
        }
    }



    override fun initUI() {
        viewModel.getSatelliteList()
        binding?.apply {

            setAdapter()

            searchView.apply {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(text: String): Boolean {

                        if(::textChangeCountDownJob.isInitialized){
                            textChangeCountDownJob.cancel()
                        }

                        textChangeCountDownJob = lifecycleScope.launch {
                            delay(SEARCH_DELAY)
                            viewModel.makeSearch(text)
                        }
                        return false
                    }
                })
            }
        }
    }

    private fun setAdapter(){
        binding?.apply {
            rv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                ContextCompat.getDrawable(context, R.drawable.divider_gray_1dp)?.let {
                    DividerItemDecorator(it)
                }?.let {
                    addItemDecoration(it)
                }
                adapter = listAdapter
            }
        }
    }

    private fun onClicked(model: SatelliteUIModel) {
        model.satelliteId?.let {
            SatelliteListFragmentDirections
                .actionListFragmentToDetailsFragment(
                    id = model.satelliteId,
                    name = model.name.orEmpty())
                .also(findNavController()::navigate)
        }
    }
}