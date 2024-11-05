package com.skincarean.android.ui.brand

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.skincarean.android.di.AppInjector
import com.skincarean.android.core.data.domain.model.brand.DetailBrand
import com.skincarean.android.databinding.FragmentAboutBrandBinding


class AboutBrandFragment : Fragment() {

    private var _binding: FragmentAboutBrandBinding? = null
    private val binding get() = _binding!!
    private lateinit var brandViewModel: BrandViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAboutBrandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()
        val brandId = brandViewModel.selectedBrandId

        Log.e("brandFragment", brandViewModel.brandDescription.toString())
        Log.e("brandFragment", brandViewModel.brandTitle.toString())


        brandId?.let { getDetailBrandByBrandId(it) }


    }

    private fun setupViewModel() {
        val factory = AppInjector.provideViewModelFactory()
        brandViewModel = ViewModelProvider(requireActivity(), factory)[BrandViewModel::class.java]
    }

    private fun setupObservers() {
        brandViewModel.detailBrand.observe(viewLifecycleOwner) {
            setupUI(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
    private fun setupUI(data: DetailBrand) {


        binding.tvInputBrandName.text = data.name
        binding.tvInputBrandDescription.text = data.description
    }

    private fun getDetailBrandByBrandId(brandId: Long) {
        brandViewModel.getDetailBrandByBrand(brandId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}