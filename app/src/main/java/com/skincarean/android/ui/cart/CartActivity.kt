package com.skincarean.android.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.source.remote.response.cart.CartItemResponse
import com.skincarean.android.databinding.ActivityCartBinding
import com.skincarean.android.ui.ViewModelFactory

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = Injector.provideViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        setupObservers()
        getAllCarts()
//        deleteAllCartItem()

    }

    private fun setupObservers() {
        viewModel.allCart.observe(this) { cartResponse ->
            if (cartResponse != null) {


                val adapter = CartAdapter(cartResponse.cartItems)
                binding.rvCart.adapter = adapter
                binding.rvCart.layoutManager = LinearLayoutManager(this)
                binding.rvCart.setHasFixedSize(true)
                binding.tvInputTotalAmount.text = cartResponse.totalPrice.toString()


                adapter.setOnItemClickCallback(object : CartAdapter.OnItemClickCallback {
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


//    private fun plusQuantity(){
//        viewModel.plusQuantity()
//    }

}