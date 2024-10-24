package com.skincarean.android.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.databinding.ActivityCartBinding
import com.skincarean.android.ui.checkout.CartCheckoutActivity
import com.skincarean.android.ui.checkout.DirectlyCheckoutActivity

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupViewModel()
        setupObservers()
        getAllCarts()
        deleteAllCartItem()
        bindingView()


    }

    private fun setupViewModel() {
        val factory = Injector.provideViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
    }

    private fun bindingView() {
        binding.btnCheckout.setOnClickListener {
            val intent = Intent(this, CartCheckoutActivity::class.java)
            startActivity(intent)
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setupObservers() {
        viewModel.cart.observe(this) { cartResponse ->
            if (cartResponse != null) {


                val adapter = CartAdapter(cartResponse.cartItems)
                binding.rvCart.adapter = adapter
                binding.rvCart.layoutManager = LinearLayoutManager(this)
                binding.rvCart.setHasFixedSize(true)
                binding.tvInputTotalAmount.text = Utilities.numberFormat(cartResponse.totalPrice)


                adapter.setOnItemClickCallback(object : OnItemClickCallback {
                    override fun onPlusClicked(cartId: Long) {
                        viewModel.plusQuantity(cartId)

                    }

                    override fun onMinusClicked(cartId: Long) {
                        viewModel.minusQuantity(cartId)
                    }

                    override fun onTrashCartItemClicked(cartId: Long) {
                        viewModel.deleteCartItem(cartId)
                    }

                })

            }
        }

        viewModel.message.observe(this) {
            Utilities.customDialog(it, this)
        }
    }

    private fun getAllCarts() {
        viewModel.getAllCarts()
    }

    //
    private fun deleteAllCartItem() {

        binding.ivTrash.setOnClickListener {
            viewModel.deleteAllCartItem()
        }

    }


}