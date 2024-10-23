package com.skincarean.android.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.source.remote.response.OrderResponse
import com.skincarean.android.databinding.ActivityDetailOrderBinding
import com.skincarean.android.ui.home.ProductAdapter
import com.skincarean.android.ui.product.detail.ProductViewModel

class DetailOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailOrderBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var productViewModel: ProductViewModel

    companion object {
        const val EXTRA_ORDER_ID = "extra_order_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = Injector.provideViewModelFactory()
        orderViewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]
        productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]

        val orderId = intent.getStringExtra(EXTRA_ORDER_ID)
        Log.e("DetailOrderActivity", orderId.toString())

        getDetailOrder(orderId)
        getAllProducts()
        setupObservers()

    }

    private fun setupObservers() {
        orderViewModel.detailOrder.observe(this) { orderResponse ->
            if (orderResponse != null) {

                val adapter = OrderProductAdapter(orderResponse.orderItems)
                binding.rvOrder.adapter = adapter
                binding.rvOrder.layoutManager = LinearLayoutManager(this)
                binding.rvOrder.setHasFixedSize(true)

                binding.tvInputOrderStatus.text = orderResponse.orderStatus
                binding.tvInputOrderId.text = orderResponse.orderId
                updateUiByOrderStatus(orderResponse)

                binding.tvInputPaymentCode.text = orderResponse.payment?.paymentCode
                binding.tvInputPaymentStatus.text = orderResponse.payment?.paymentStatus
                binding.tvInputPaidDate.text = orderResponse.payment?.paidDate.toString()
                binding.tvInputShippingAddress.text = orderResponse.shippingAddress
                binding.tvInputShippingPrice.text =
                    Utilities.numberFormat(orderResponse.shippingCost)
                binding.tvInputTax.text = Utilities.numberFormat(orderResponse.tax)
                binding.tvInputTotalPaid.text =
                    Utilities.numberFormat(orderResponse.payment?.totalPaid)
                binding.tvInputTotalPesanan.text = Utilities.numberFormat(orderResponse.finalPrice)


            }
        }
        productViewModel.listProduct.observe(this) { listProductResponses ->
            val adapter = ProductAdapter(listProductResponses.shuffled())
            binding.rvMaybeULike.adapter = adapter
            binding.rvMaybeULike.layoutManager =
                GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            binding.rvMaybeULike.setHasFixedSize(true)


        }
    }

    private fun getDetailOrder(orderId: String?) {
        if (orderId != null) {
            orderViewModel.detailOrder(orderId)
        }

    }

    private fun getAllProducts() {
        productViewModel.getAllProduct()
    }

    private fun updateUiByOrderStatus(orderResponse: OrderResponse) {
        if (orderResponse.orderStatus == "Selesai") {
            binding.layoutPaymentCode.visibility = View.GONE
            binding.tvInputOrderStatus.setBackgroundColor(resources.getColor(R.color.mint_green_80))

        } else {
            binding.tvInputPaidDate.visibility = View.GONE
            binding.tvInputTotalPaid.visibility = View.GONE
            binding.tvPaidDate.visibility = View.GONE
            binding.tvTotalPaid.visibility = View.GONE
            binding.tvInputOrderStatus.setBackgroundColor(resources.getColor(R.color.red_80))
            binding.tvInputPaymentStatus.setTextColor(resources.getColor(R.color.red_80))
        }

    }

}