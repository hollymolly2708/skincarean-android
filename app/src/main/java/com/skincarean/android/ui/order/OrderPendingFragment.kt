package com.skincarean.android.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skincarean.android.R
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.databinding.FragmentOrderBinding
import com.skincarean.android.databinding.FragmentOrderPendingBinding


class OrderPendingFragment : Fragment() {

    private var _binding: FragmentOrderPendingBinding? = null
    private val binding get() = _binding!!

    //
    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderPendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = Injector.provideViewModelFactory()
        viewModel = ViewModelProvider(requireActivity(), factory)[OrderViewModel::class.java]
        getAllPendingOrders()
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.allPendingOrders.observe(viewLifecycleOwner) { listOrderResponse ->

            val orderAdapter = OrderAdapter(listOrderResponse)
            listOrderResponse.forEach {


                OrderProductAdapter(it.orderItems)
            }


            binding.rvOrder.adapter = orderAdapter
            binding.rvOrder.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvOrder.setHasFixedSize(true)
        }
    }

    private fun getAllPendingOrders() {
        viewModel.getAllPendingOrders()
    }
}

