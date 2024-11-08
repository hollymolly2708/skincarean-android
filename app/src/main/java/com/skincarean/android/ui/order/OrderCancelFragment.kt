package com.skincarean.android.ui.order

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.di.AppInjector
import com.skincarean.android.core.data.domain.model.order.Order
import com.skincarean.android.databinding.FragmentOrderCancelBinding

class OrderCancelFragment : Fragment() {
    private var _binding: FragmentOrderCancelBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderViewModel: OrderViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderCancelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = AppInjector.provideViewModelFactory()
        orderViewModel = ViewModelProvider(requireActivity(), factory)[OrderViewModel::class.java]
        setUpObservers()
        getAllCancelOrders()
    }

    private fun setUpObservers() {
        orderViewModel.allCancelOrders.observe(viewLifecycleOwner) { orders ->
            setCancelOrders(orders)
        }
        orderViewModel.isLoading.observe(viewLifecycleOwner) {
            setLoading(it)
        }
    }

    private fun setCancelOrders(orders: List<Order>) {
        orders.forEach {
            val adapter = OrderProductAdapter()
            adapter.submitList(it.orderItems)
        }
        val adapter = OrderAdapter()
        adapter.submitList(orders)
        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onOrderClickCallback(data: Order) {

                val intent = Intent(requireContext(), DetailOrderActivity::class.java)
                intent.putExtra(DetailOrderActivity.EXTRA_ORDER_ID, data.orderId)
                startActivity(intent)
            }
        })
        binding.rvOrder.adapter = adapter
        binding.rvOrder.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvOrder.setHasFixedSize(true)
    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            binding.rvOrder.visibility = View.GONE
            binding.ivLoading.visibility = View.VISIBLE
        } else {
            binding.rvOrder.visibility = View.VISIBLE
            binding.ivLoading.visibility = View.GONE
        }
    }

    private fun getAllCancelOrders() {
        orderViewModel.getAllCancelOrders()
    }

    override fun onResume() {
        super.onResume()
        orderViewModel.getAllCancelOrders()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}