package com.skincarean.android.ui.checkout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.skincarean.android.core.data.LoginSharedPref
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.source.remote.request.DirectlyOrderRequest
import com.skincarean.android.core.data.source.remote.response.payment_method.PaymentMethodResponse
import com.skincarean.android.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
        const val EXTRA_QUANTITY = "extra_quantity"

    }

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var viewModel: CheckoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LoginSharedPref.checkSession(this)
        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID)
        val quantity = intent.getIntExtra(EXTRA_QUANTITY, 1)

        val factory = Injector.provideViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[CheckoutViewModel::class.java]

        getAllPaymentMethods(productId)
        setupObservers(productId!!, quantity)
        checked(productId)

        binding.btnCheckout.setOnClickListener {
            val directlyOrderRequest = DirectlyOrderRequest(
                productId,
                quantity,
                viewModel._selectedPaymentMethodId!!,
                "apa",
                "dimana"
            )
            directlyCheckout(directlyOrderRequest)
        }
    }

    private fun getAllPaymentMethods(productId: String?) {
        viewModel.getAllPaymentMethods()
        if (productId != null) {
            viewModel.getDetailProduct(productId)
        }

    }

    private fun setupObservers(productId: String, quantity: Int) {
        viewModel.allPaymentMethods.observe(this) { paymentMethods ->
            if (paymentMethods != null && paymentMethods.isNotEmpty()) {
                val adapter = PaymentMethodAdapter(paymentMethods)
                binding.rvPaymentMethod.adapter = adapter
                binding.rvPaymentMethod.layoutManager = LinearLayoutManager(this)
                binding.rvPaymentMethod.setHasFixedSize(true)

                adapter.setOnItemClickCallback(object : PaymentMethodAdapter.OnItemClickCallback {
                    override fun onClicked(data: PaymentMethodResponse) {
                        viewModel._selectedPaymentMethodId = data.id
                        viewModel._selectedDescription = data.description
                        binding.btnCheckout.isEnabled = true
                        Toast.makeText(
                            this@CheckoutActivity,
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

        viewModel.detailProduct.observe(this) { data ->
            if (data != null) {
                val uri = Uri.parse(data.thumbnailImage)
                Glide.with(binding.root)
                    .load(uri)
                    .centerCrop()
                    .into(binding.ivInputProduct)
                val totalPrice = data.price!!.toInt().times(quantity)

                binding.tvInputQuantity.text = quantity.toString()
                binding.tvInputPrice.text = data.price.toString()
                binding.tvInputTotalPrice.text =
                    totalPrice.toString()

                binding.tvInputTotalPembayaran.text = totalPrice.toString()
                binding.tvTitleInputProduct.text = data.productName
            }

        }

        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }


    }

    private fun checked(productId: String?) {
        if (!productId.isNullOrEmpty()) {
            binding.layoutListProductItem.visibility = View.GONE

        } else {
            binding.layoutListProductItem.visibility = View.VISIBLE
        }

    }

    private fun directlyCheckout(directlyOrderRequest: DirectlyOrderRequest) {
        viewModel.directlyOrderRequest(directlyOrderRequest)
    }
}