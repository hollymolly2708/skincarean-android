package com.skincarean.android.ui.product.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.databinding.FragmentProductReviewBinding

class ReviewProductFragment : Fragment() {
    companion object {
        var productId: String = ""
    }


    private var _binding: FragmentProductReviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = Injector.provideViewModelFactory()
        viewModel =
            ViewModelProvider(requireActivity(), factory)[ProductViewModel::class.java]
        setupObservers()
        val stringExtra =
            requireActivity().intent.getStringExtra(DetailProductActivity.EXTRA_PRODUCT_ID)

        productId = stringExtra.toString()
        getProductReviews(productId)

    }

    fun setupObservers() {
        viewModel.allReviews.observe(viewLifecycleOwner) { data ->
            val adapter = ReviewAdapter(data)
            binding.rvReviews.adapter = adapter
            binding.rvReviews.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvReviews.setHasFixedSize(true)
        }
    }

    fun getProductReviews(productId: String) {
        viewModel.getAllReviews(productId)
    }

}