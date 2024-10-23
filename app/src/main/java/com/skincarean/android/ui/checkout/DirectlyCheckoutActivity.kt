package com.skincarean.android.ui.checkout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.LoginSharedPref
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.domain.model.payment_method.PaymentMethod
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest
import com.skincarean.android.databinding.ActivityDirectlyCheckoutBinding
import com.skincarean.android.ui.LoadingActivity
import com.skincarean.android.ui.order.DetailOrderActivity
import com.skincarean.android.ui.order.OrderViewModel

class DirectlyCheckoutActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
        const val EXTRA_QUANTITY = "extra_quantity"

    }

    private lateinit var binding: ActivityDirectlyCheckoutBinding
    private lateinit var checkoutViewModel: CheckoutViewModel
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDirectlyCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LoginSharedPref.checkSession(this)
        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID)
        val quantity: Int = intent.getIntExtra(EXTRA_QUANTITY, 1)

        val factory = Injector.provideViewModelFactory()
        checkoutViewModel = ViewModelProvider(this, factory)[CheckoutViewModel::class.java]
        orderViewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]

        if (productId != null) {
            getAllPaymentMethods(productId)
            setupObservers(productId, quantity)
            sendOrder(productId, quantity)
        }


    }

    private fun getAllPaymentMethods(productId: String?) {
        checkoutViewModel.getAllPaymentMethods()
        if (productId != null) {
            checkoutViewModel.getDetailProduct(productId)
        }

    }

    private fun setupObservers(productId: String?, quantity: Int) {
        checkoutViewModel.listPaymentMethods.observe(this) { paymentMethods ->
            if (paymentMethods != null && paymentMethods.isNotEmpty()) {
                val adapter = PaymentMethodAdapter(paymentMethods)
                binding.rvPaymentMethod.adapter = adapter
                binding.rvPaymentMethod.layoutManager = LinearLayoutManager(this)
                binding.rvPaymentMethod.setHasFixedSize(true)

                adapter.setOnItemClickCallback(object : OnItemClickCallback {
                    override fun onPaymentMethodClickCallback(data: PaymentMethod) {
                        checkoutViewModel._selectedPaymentMethodId = data.id
                        checkoutViewModel._selectedDescription = data.description
                        binding.btnCheckout.isEnabled = true
                        Toast.makeText(
                            this@DirectlyCheckoutActivity,
                            "Metode pembayaran dipilih: ${data.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } else {
                Toast.makeText(this, "Gagal memuat metode pembayaran", Toast.LENGTH_SHORT).show()
                // Pastikan untuk menonaktifkan tombol checkout jika tidak ada metode pembayaran
                binding.btnCheckout.isEnabled = false
            }
        }

        checkoutViewModel.detailProduct.observe(this) { data ->
            if (data != null) {
                val uri = Uri.parse(data.thumbnailImage)
                Glide.with(binding.root)
                    .load(uri)
                    .centerCrop()
                    .into(binding.ivInputProduct)
                val totalPrice = data.price!!.toInt().times(quantity).toBigDecimal()

                binding.tvInputQuantity.text = quantity.toString()
                binding.tvInputPrice.text = Utilities.numberFormat(data.price)
                binding.tvInputTotalPrice.text =
                    Utilities.numberFormat(totalPrice)

                binding.tvInputTotalPembayaran.text = Utilities.numberFormat(totalPrice)
                binding.tvTitleInputProduct.text = data.productName
            }

        }

        checkoutViewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        checkoutViewModel.orderId.observe(this) { it ->
            val orderId = it["orderId"]
            val intent = Intent(this, LoadingActivity::class.java)
            intent.putExtra(DetailOrderActivity.EXTRA_ORDER_ID, orderId.toString())
            startActivity(intent)
            finish()
        }


    }

    private fun directlyCheckout(directlyOrderRequest: DirectlyOrderRequest) {
        checkoutViewModel.directlyOrder(directlyOrderRequest)
    }

    private fun sendOrder(productId: String?, quantity: Int) {
        binding.btnCheckout.setOnClickListener {
            val paymentMethodId = checkoutViewModel._selectedPaymentMethodId
            val shippingAddress = binding.edtInputAddress.text
            if (!productId.isNullOrEmpty()) {
                if (paymentMethodId != null) {
                    if (!shippingAddress.isNullOrEmpty()) {
                        val directlyOrderRequest = DirectlyOrderRequest(
                            productId,
                            quantity,
                            paymentMethodId,
                            binding.edInputMessage.text.toString(),
                            shippingAddress.toString()
                        )
                        directlyCheckout(directlyOrderRequest)
                    } else {
                        Utilities.customDialog("Tolong isikan alamat", this)
                    }

                } else {
                    Utilities.customDialog("Tolong pilih metode pembayaran", this)
                }
            }
        }
    }
}