package com.skincarean.android.ui.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.domain.model.payment_method.PaymentMethod
import com.skincarean.android.core.data.source.remote.request.CartOrderRequest
import com.skincarean.android.core.data.source.remote.response.cart.CartResponse
import com.skincarean.android.databinding.ActivityCartCheckoutBinding
import com.skincarean.android.ui.LoadingActivity
import com.skincarean.android.ui.cart.CartViewModel
import com.skincarean.android.ui.order.DetailOrderActivity
import com.skincarean.android.ui.order.OrderViewModel

class CartCheckoutActivity : AppCompatActivity() {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var checkoutViewModel: CheckoutViewModel
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var binding: ActivityCartCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeViewModel()


        getAllCarts()
        getAllPaymentMethods()
        setupObserver()
        checkout()

    }

    private fun initializeViewModel() {
        val factory = Injector.provideViewModelFactory()
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
        checkoutViewModel = ViewModelProvider(this, factory)[CheckoutViewModel::class.java]
        orderViewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]
    }

    private fun setupCartItemAdapter(carts: CartResponse) {

        val cartCheckoutAdapter = CartCheckoutAdapter(carts.cartItems)
        binding.rvCart.apply {
            adapter = cartCheckoutAdapter
            layoutManager = LinearLayoutManager(this@CartCheckoutActivity)
            setHasFixedSize(true)

        }
        binding.tvInputTotalPembayaranInCart.text = Utilities.numberFormat(carts.totalPrice)

    }

    private fun setupPaymentMethodAdapter(paymentMethods: List<PaymentMethod>) {

        val paymentMethodAdapter = PaymentMethodAdapter(paymentMethods)

        binding.rvPaymentMethod.apply {
            adapter = paymentMethodAdapter
            layoutManager = LinearLayoutManager(this@CartCheckoutActivity)
            setHasFixedSize(true)
        }

        paymentMethodAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onPaymentMethodClickCallback(data: PaymentMethod) {
                checkoutViewModel._selectedPaymentMethodId = data.id
            }

        })
    }

    private fun setupObserver() {
        checkoutViewModel.listPaymentMethods.observe(this) {
            setupPaymentMethodAdapter(it)
        }

        cartViewModel.allCart.observe(this) {
            setupCartItemAdapter(it)
        }


        checkoutViewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        checkoutViewModel.orderId.observe(this) { data ->
            navigateToLoadingUi(data["orderId"])
        }

    }

    private fun navigateToLoadingUi(orderId: Any?) {
        val intent = Intent(this, LoadingActivity::class.java)
        finish()
        intent.putExtra(DetailOrderActivity.EXTRA_ORDER_ID, orderId.toString())
        startActivity(intent)

    }

    private fun getAllCarts() {
        cartViewModel.getAllCarts()
    }

    private fun getAllPaymentMethods() {
        checkoutViewModel.getAllPaymentMethods()
    }

    private fun checkout() {

        binding.btnCheckout.setOnClickListener {

            val paymentMethodId = checkoutViewModel._selectedPaymentMethodId
            if (paymentMethodId != null) {
                if (!binding.edtInputAddress.text.isNullOrEmpty()) {

                    val cartOrderRequest = CartOrderRequest(
                        paymentMethodId,
                        binding.edtMessage.text.toString(),
                        binding.edtInputAddress.text.toString()
                    )
                    checkoutViewModel.cartOrder(cartOrderRequest)
                } else {
                    Utilities.customDialog("Tolong isikan alamat pengiriman", this)
                }

            } else {
                Utilities.customDialog("Tolong pilih metode pembayaran", this)
            }

        }
    }

}