package com.skincarean.android.ui.brand

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.R
import com.skincarean.android.di.Injector
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.databinding.FragmentProductByBrandBinding
import com.skincarean.android.ui.home.ProductAdapter
import com.skincarean.android.ui.product.detail.DetailProductActivity

class ProductByBrandFragment : Fragment() {

    private var _binding: FragmentProductByBrandBinding? = null
    private val binding get() = _binding!!
    private lateinit var brandViewModel: BrandViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductByBrandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()

        brandViewModel.selectedBrandId?.let { getProductsByBrand(it) }

        setupObservers()
        Log.e("brandId", brandViewModel.selectedBrandId.toString())


    }

    private fun setupObservers() {
        brandViewModel.allProductsByBrand.observe(viewLifecycleOwner) {
            setupUI(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setupUI(products: List<Product>) {
        val adapter = ProductAdapter()
        adapter.submitList(products)
        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onProductClickCallback(data: Product) {
                val intent = Intent(requireActivity(), DetailProductActivity::class.java)
                intent.putExtra(DetailProductActivity.EXTRA_PRODUCT_ID, data.productId)
                startActivity(intent)
            }
        })
        binding.rvProductByBrand.adapter = adapter
        binding.rvProductByBrand.layoutManager =
            GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        binding.rvProductByBrand.setHasFixedSize(true)
    }

    private fun setupViewModel() {
        val factory = Injector.provideViewModelFactory()
        brandViewModel = ViewModelProvider(requireActivity(), factory)[BrandViewModel::class.java]
    }

    private fun getProductsByBrand(brandId: Long?) {
        if (brandId != null) {
            brandViewModel.getAllProductsByBrand(brandId)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}