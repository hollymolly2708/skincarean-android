package com.skincarean.android.ui.product

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.databinding.FragmentProductBinding
import com.skincarean.android.ui.cart.CartActivity
import com.skincarean.android.ui.home.ProductAdapter
import com.skincarean.android.ui.product.detail.DetailProductActivity
import com.skincarean.android.ui.product.detail.ProductViewModel


class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var productViewModel: ProductViewModel
    private val productAdapter = ProductAdapter()
    private val productShimmerAdapter = ProductShimmerAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = Injector.provideViewModelFactory()
        productViewModel =
            ViewModelProvider(requireActivity(), factory)[ProductViewModel::class.java]
        getAllProduct()
        setupObservers()
        searchListener()
        bindingView()
        setupShimmerProduct()

    }

    private fun setupObservers() {
        productViewModel.listProduct.observe(viewLifecycleOwner) { products ->
            setupListProduct(products)
        }
        productViewModel.loading.observe(viewLifecycleOwner) {
            setupLoading(it)
        }

    }

    private fun setupLoading(loading: Boolean) {
        if (loading) {
            binding.rvProduct.visibility = View.GONE
            binding.rvShimmerProduct.visibility = View.VISIBLE
        } else {
            binding.rvProduct.visibility = View.VISIBLE
            binding.rvShimmerProduct.visibility = View.GONE
        }
    }

    private fun setupShimmerProduct() {
        binding.rvShimmerProduct.adapter = productShimmerAdapter
        binding.rvShimmerProduct.layoutManager =
            GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        binding.rvProduct.setHasFixedSize(true)
    }

    private fun setupListProduct(products: List<Product>) {

        productAdapter.submitList(products)
        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.layoutManager =
            GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        binding.rvProduct.setHasFixedSize(true)




        productAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onProductClickCallback(data: Product) {
                val intent = Intent(requireActivity(), DetailProductActivity::class.java)
                intent.putExtra(DetailProductActivity.EXTRA_PRODUCT_ID, data.productId)
                startActivity(intent)
            }
        })
    }

    private fun bindingView() {
        binding.ivCartProduct.setOnClickListener {
            val intent = Intent(requireActivity(), CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAllProduct() {
        productViewModel.getAllProduct()
    }

    private fun searchProduct(nameProduct: String?) {
        if (nameProduct != null) {
            productViewModel.searchProduct(nameProduct, 0, 10)
        }

    }

    private fun searchListener() {
        binding.searchProduct.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchProduct(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchProduct(newText)
                return false
            }

        })
    }

}