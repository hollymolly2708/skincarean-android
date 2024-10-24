package com.skincarean.android.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.domain.model.order.DetailOrder
import com.skincarean.android.core.data.domain.model.order.Order
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

        supportActionBar?.title = "Detail Order Activity"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupObservers() {
        orderViewModel.detailOrder.observe(this) { order ->
            if (order != null) {

                val adapter = OrderProductAdapter(order.orderItems)
                binding.rvOrder.adapter = adapter
                binding.rvOrder.layoutManager = LinearLayoutManager(this)
                binding.rvOrder.setHasFixedSize(true)

                binding.tvInputOrderStatus.text = order.orderStatus
                binding.tvInputOrderId.text = order.orderId
                updateUiByOrderStatus(order)

                binding.tvInputPaymentCode.text = order.payment?.paymentCode
                binding.tvInputPaymentStatus.text = order.payment?.paymentStatus
                binding.tvInputPaidDate.text = order.payment?.paidDate.toString()
                binding.tvInputShippingAddress.text = order.shippingAddress
                binding.tvInputShippingPrice.text =
                    Utilities.numberFormat(order.shippingCost)
                binding.tvInputTax.text = Utilities.numberFormat(order.tax)
                binding.tvInputPaymentMethodName.text = order.payment?.paymentMethodName
                binding.tvInputTotalPaid.text =
                    Utilities.numberFormat(order.payment?.totalPaid)
                binding.tvInputTotalPesanan.text = Utilities.numberFormat(order.finalPrice)


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

    private fun updateUiByOrderStatus(detailOrder: DetailOrder) {
        if (detailOrder.orderStatus == "Selesai") {
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