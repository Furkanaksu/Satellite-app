package com.furkan.satellite_app.features.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.furkan.satellite_app.utils.Progress

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding

    abstract val viewModel: VM

    abstract fun layoutResource(inflater: LayoutInflater, container: ViewGroup?): VB
    open fun observeViewModel() {}
    open fun initUI() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = layoutResource(inflater, container)
        observeViewModel()
        initUI()
        return binding?.root
    }

    fun showLoading() = Progress.show(requireContext())

    fun hideLoading() = Progress.dismiss()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}