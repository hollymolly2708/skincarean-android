package com.skincarean.android.ui.product.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.skincarean.android.R
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.databinding.FragmentDetailProductBinding
import com.skincarean.android.databinding.FragmentProductReviewBinding


class DetailProductFragment : Fragment() {


    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var productViewModel: ProductViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()

    }

    private fun setupViewModel() {
        val factory = Injector.provideViewModelFactory()
        productViewModel =
            ViewModelProvider(requireActivity(), factory)[ProductViewModel::class.java]
    }

    private fun setupObservers() {
        productViewModel.product.observe(requireActivity()) { detailProduct ->
            bindingViews(detailProduct)
        }
    }

    private fun bindingViews(detailProduct: DetailProduct) {
        binding.tvInputSize.text = detailProduct.size.toString()
        binding.tvInputIngredients.text = detailProduct.ingredient
        binding.tvInputName.text = detailProduct.productName
    }
    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}