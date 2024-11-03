package com.skincarean.android.ui.cart

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.di.Injector
import com.skincarean.android.databinding.ActivityCartBinding
import com.skincarean.android.ui.checkout.CartCheckoutActivity
import com.skincarean.android.ui.checkout.DirectlyCheckoutActivity
import java.math.BigDecimal

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

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setupObservers() {
        viewModel.cart.observe(this) { cartResponse ->
            if (cartResponse != null) {
                if (cartResponse.totalPrice != null) {
                    if (cartResponse.totalPrice == BigDecimal.ZERO) {
                        binding.btnCheckout.isEnabled = false
                        binding.btnCheckout.alpha = 0.7f

                    } else {
                        binding.btnCheckout.isEnabled = true
                        binding.btnCheckout.alpha = 1.0f
                        binding.btnCheckout.setOnClickListener {
                            val intent = Intent(this, CartCheckoutActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }

                val adapter = CartAdapter()
                adapter.submitList(cartResponse.cartItems)

                binding.rvCart.adapter = adapter
                binding.rvCart.layoutManager = LinearLayoutManager(this)
                binding.rvCart.setHasFixedSize(true)

                binding.rvCart.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                    }
                })


                binding.tvInputTotalAmount.text = Utilities.numberFormat(cartResponse.totalPrice)


                adapter.setOnItemClickCallback(object : OnItemClickCallback {
                    override fun onPlusClicked(cartId: Long) {
                        viewModel.plusQuantity(cartId)

                    }

                    override fun onCheckBoxCartItemClicked(cartId: Long) {
                        viewModel.setActiveCart(cartId)
                    }

                    override fun onMinusClicked(cartId: Long) {
                        viewModel.minusQuantity(cartId)
                    }

                    override fun onTrashCartItemClicked(cartId: Long) {
                        AlertDialog.Builder(this@CartActivity)
                            .setTitle("Hapus Item")
                            .setMessage("Apakah anda yakin ingin menghapus item ini dari keranjang ?")
                            .setNegativeButton(
                                "Batal"
                            ) { p0, p1 -> p0?.dismiss() }
                            .setPositiveButton(
                                "OK"
                            ) { p0, p1 -> viewModel.deleteCartItem(cartId) }.show()

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
            AlertDialog.Builder(this)
                .setTitle("Hapus Item")
                .setMessage("Apakah anda yakin ingin menghapus semua item ?")
                .setPositiveButton(
                    "Ok"
                ) { p0, p1 -> viewModel.deleteAllCartItem() }.setNegativeButton(
                    "Batal"
                ) { p0, p1 -> p0.dismiss() }.show()

        }

    }


}