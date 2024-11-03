package com.skincarean.android.ui.order

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.di.Injector
import com.skincarean.android.core.data.domain.model.order.DetailOrder
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.databinding.ActivityDetailOrderBinding
import com.skincarean.android.ui.home.ProductAdapter
import com.skincarean.android.ui.product.detail.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailOrderBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var productViewModel: ProductViewModel

    private val productAdapter = ProductAdapter()
    private val orderProductAdapter = OrderProductAdapter()

    companion object {
        const val EXTRA_ORDER_ID = "extra_order_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupViewModel()
        val orderId = intent.getStringExtra(EXTRA_ORDER_ID)

        CoroutineScope(Dispatchers.IO).launch {
            getDetailOrder(orderId)
            getAllProducts()
        }

        setupObservers()
        isSwipe(orderId)

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

    private fun setupViewModel() {
        val factory = Injector.provideViewModelFactory()
        orderViewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]
        productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
    }

    private fun isSwipe(orderId: String?) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (orderId != null) {


                CoroutineScope(Dispatchers.IO).launch {
                    orderViewModel.detailOrder(orderId)
                }

            }

            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupObservers() {
        orderViewModel.detailOrder.observe(this) { order ->
            setupDetailOrder(order)
        }
        productViewModel.listProduct.observe(this) { products ->
            setupProduct(products)
        }
        productViewModel.loading.observe(this) {
            setupLoading(it)
        }
    }

    private fun setupLoading(loading: Boolean) {
        if (loading) {
            binding.layoutRootDetailOrder.visibility = View.GONE
            binding.ivLoading.visibility = View.VISIBLE
        } else {

            binding.ivLoading.visibility = View.GONE
            binding.layoutRootDetailOrder.visibility = View.VISIBLE
        }
    }

    private fun setupDetailOrder(order: DetailOrder) {


        orderProductAdapter.submitList(order.orderItems)
        binding.rvOrder.adapter = orderProductAdapter
        binding.rvOrder.layoutManager = LinearLayoutManager(this)
        binding.rvOrder.setHasFixedSize(false)

        binding.tvInputOrderStatus.text = order.orderStatus
        binding.tvInputOrderId.text = order.orderId
        updateUiByOrderStatus(order)

        binding.tvInputPaymentCode.text = order.payment?.paymentCode
        binding.tvInputPaymentStatus.text = order.payment?.paymentStatus
        binding.tvInputShippingAddress.text = order.shippingAddress
        binding.tvInputShippingPrice.text = Utilities.numberFormat(order.shippingCost)
        binding.tvInputTax.text = Utilities.numberFormat(order.tax)
        binding.tvInputPaymentMethodName.text = order.payment?.paymentMethodName
        binding.tvInputTotalPaid.text = Utilities.numberFormat(order.payment?.totalPaid)
        binding.tvInputTotalPesanan.text = Utilities.numberFormat(order.finalPrice)

        if (order.payment?.paidDate != null) {
            binding.tvInputPaidDate.text =
                Utilities.convertIso8601ToDate(order.payment?.paidDate.toString())
        }


    }

    private fun setupProduct(products: List<Product>) {

        productAdapter.submitList(products.shuffled())
        binding.rvMaybeULike.adapter = productAdapter
        binding.rvMaybeULike.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.rvMaybeULike.setHasFixedSize(false)

    }

    private suspend fun getDetailOrder(orderId: String?) {
        if (orderId != null) {
            orderViewModel.detailOrder(orderId)
        }

    }

    private suspend fun getAllProducts() {
        productViewModel.getAllProduct()
    }

    private fun updateUiByOrderStatus(detailOrder: DetailOrder) {
        if (detailOrder.orderStatus == "Selesai") {
            binding.apply {
                layoutPaymentCode.visibility = View.GONE
                tvInputOrderStatus.setBackgroundColor(resources.getColor(R.color.mint_green_80))
                tvInputPaymentStatus.setTextColor(
                    ContextCompat.getColor(
                        this@DetailOrderActivity, R.color.mint_green_80
                    )
                )
                tvInputPaidDate.visibility = View.VISIBLE
                tvInputTotalPaid.visibility = View.VISIBLE
                tvPaidDate.visibility = View.VISIBLE
                tvTotalPaid.visibility = View.VISIBLE
                btnCancel.visibility = View.GONE
            }


        } else {
            binding.apply {
                tvInputPaidDate.visibility = View.GONE
                tvInputTotalPaid.visibility = View.GONE
                tvPaidDate.visibility = View.GONE
                tvTotalPaid.visibility = View.GONE
                tvInputOrderStatus.setBackgroundColor(resources.getColor(R.color.red_80))
                tvInputPaymentStatus.setTextColor(resources.getColor(R.color.red_80))
                btnCancel.visibility = View.VISIBLE
            }


        }

    }

}