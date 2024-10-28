package com.skincarean.android.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.ui.product.detail.DetailProductActivity
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.domain.model.brand.Brand
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.user.User
import com.skincarean.android.databinding.FragmentHomeBinding
import com.skincarean.android.ui.brand.DetailBrandActivity
import com.skincarean.android.ui.cart.CartActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = Injector.provideViewModelFactory()
        homeViewModel =
            ViewModelProvider(requireActivity(), factory = factory)[HomeViewModel::class.java]

        setupObservers()
        setupTopBrand()
        setupPopularProduct()
        bindingView()
        setupUserProfile()
    }

    private fun bindingView() {
        binding.ivCart.setOnClickListener {
            val intent = Intent(requireContext(), CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupTopBrand() {
        homeViewModel.getAllBrandByTopBrand()
    }

    private fun setupPopularProduct() {
        homeViewModel.getAllPopularProduct()
    }

    private fun setupUserProfile() {
        homeViewModel.getCurrentUser()
    }

    private fun setupObservers() {

        homeViewModel.currentUser.observe(viewLifecycleOwner) {
            setupUser(it)
        }
        homeViewModel.errorMessage.observe(viewLifecycleOwner) {
            Utilities.customDialog(it, requireActivity())
        }

        homeViewModel.allBrandByTopBrand.observe(viewLifecycleOwner) { data ->

            val adapter = TopBrandAdapter(data)
            binding.rvTopBrand.layoutManager =
                GridLayoutManager(requireActivity(), 2, GridLayoutManager.HORIZONTAL, false)
            binding.rvTopBrand.setHasFixedSize(true)
            binding.rvTopBrand.adapter = adapter

            adapter.setUpOnItemClickCallback(object : OnItemClickCallback {
                override fun onTopBrandClickCallback(data: Brand) {
                    val intent = Intent(requireContext(), DetailBrandActivity::class.java)
                    intent.putExtra(DetailBrandActivity.EXTRA_BRAND_ID, data.id)

                    startActivity(intent)
                }
            })


        }

        homeViewModel.allPopularProduct.observe(viewLifecycleOwner) { data ->
            val adapter = ProductAdapter(data.shuffled())
            adapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onProductClickCallback(data: Product) {
                    val intent = Intent(requireActivity(), DetailProductActivity::class.java)
                    intent.putExtra(DetailProductActivity.EXTRA_PRODUCT_ID, data.productId)

                    startActivity(intent)
                }

            })
            binding.rvPopularProduct.adapter = adapter
            binding.rvPopularProduct.layoutManager =
                GridLayoutManager(requireActivity(), 2, GridLayoutManager.HORIZONTAL, false)
            binding.rvPopularProduct.setHasFixedSize(true)
        }
    }

    private fun setupUser(data: User) {
        val ivUser = binding.ivUser
        val uri = Uri.parse(data.profilePicture)
        Glide.with(requireActivity())
            .load(uri)
            .timeout(60000)
            .circleCrop()
            .into(ivUser)
        binding.tvFullNameUser.text = data.fullName

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}