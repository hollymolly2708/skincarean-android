package com.skincarean.android.ui.product.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.setMargins
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.core.data.LoginSharedPref
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.source.remote.request.CartRequest
import com.skincarean.android.databinding.ActivityDetailProductBinding
import com.skincarean.android.ui.cart.CartActivity
import com.skincarean.android.ui.cart.CartViewModel
import com.skincarean.android.ui.home.ProductAdapter
import com.skincarean.android.ui.checkout.DirectlyCheckoutActivity

class DetailProductActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private var quantity: Int = 0

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 0)
    }

    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailProductTabLayoutAdapter = DetailProductTabLayoutAdapter(this)

        val viewpager = binding.viewpagerDetailProduct

        viewpager.adapter = detailProductTabLayoutAdapter

        val tabs = binding.tabLayoutDetailProduct

        LoginSharedPref.checkSession(this)

        val tabNames = arrayOf("Description", "Details")
        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()


        val factory = Injector.provideViewModelFactory()
        viewModel = ViewModelProvider(this, factory = factory)[ProductViewModel::class.java]
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID)


        if (productId != null) {
            getProductById(productId)
            sendOrder(productId)
            binding.btnCart.setOnClickListener {
                val cartRequest = CartRequest(productId, 1)
                cartViewModel.addProductToCart(cartRequest)
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
        }

        setupObservers()
        getAllProducts()
        quantityCount()


    }

    private fun getProductById(productId: String) {
        viewModel.getProductByProductId(productId)

    }


    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.product.observe(this) { data ->
            if (data != null) {
                val uri = Uri.parse(data.thumbnailImage)

//            Glide.with(this)
//                .load(uri)
//                .timeout(60000)
//                .into(binding.ivDetailProduct)
                binding.tvInputNameProduct.text = data.productName
                binding.tvInputStok.text = data.stok.toString()
                binding.tvInputCategory.text = data.categoryName
                binding.tvInputOriginalPriceInDiscount.text =
                    Utilities.numberFormat(data.originalPrice)
                binding.tvInputCategory.text = data.categoryName
                binding.tvInputDiscount.text = "${data.discount.toString()}%"
                binding.tvInputTotalPriceInDiscount.text = Utilities.numberFormat(data.price)




                if (data.productImage != null) {
                    val imageAdapter = ProductItemImageAdapter()
                    binding.vpProductImage.adapter = imageAdapter
                    imageAdapter.submitList(data.productImage)

                    val dotsImage = Array(data.productImage.size) {
                        ImageView(this)
                    }
                    dotsImage.forEach {
                        it.setImageResource(R.drawable.non_active_dot)
                        binding.slideDotLL.addView(it, params)
                    }


                    //default first dot selected
                    dotsImage[0].setImageResource(R.drawable.active_dot)

                    pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            dotsImage.mapIndexed { index, imageView ->

                                if (position == index) {
                                    imageView.setImageResource(R.drawable.active_dot)
                                } else {
                                   imageView.setImageResource(R.drawable.non_active_dot)
                                }
                            }
                            super.onPageSelected(position)
                        }
                    }
                    binding.vpProductImage.registerOnPageChangeCallback(pageChangeListener)
                }

            }


        }


        viewModel.listProduct.observe(this) { data ->
            val adapter = ProductAdapter(data.shuffled())
            adapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onProductClickCallback(data: Product) {
                    val intent =
                        Intent(this@DetailProductActivity, DetailProductActivity::class.java)
                    intent.putExtra(EXTRA_PRODUCT_ID, data.productId)
                    startActivity(intent)
                }
            })
            binding.rvMaybeULike.adapter = adapter
            binding.rvMaybeULike.layoutManager =
                GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)

        }


    }

    private fun quantityCount() {


        if (quantity <= 0) {
            binding.btnMinus.isEnabled = false
        }
        binding.btnPlus.setOnClickListener {
            quantity += 1
            binding.tvInputQuantity.text = quantity.toString()
            binding.btnMinus.isEnabled = true

        }
        binding.btnMinus.setOnClickListener {
            quantity -= 1
            binding.tvInputQuantity.text = quantity.toString()
            if (quantity <= 0) {
                binding.btnMinus.isEnabled = false
            }

        }


    }

    private fun sendOrder(productId: String) {

        val stok = 20

        binding.btnToOrder.setOnClickListener {
            var isFailed = false
            if (quantity == 0) {
                Utilities.customDialog("Quantity tidak boleh kosong", this)
                isFailed = true
            }
            if (quantity > stok) {
                Utilities.customDialog("Jumlah yang dipesan tidak boleh lebih dari Stok", this)
                isFailed = true
            }

            if (!isFailed) {
                val intent = Intent(this, DirectlyCheckoutActivity::class.java)
                intent.putExtra(DirectlyCheckoutActivity.EXTRA_QUANTITY, quantity)
                intent.putExtra(DirectlyCheckoutActivity.EXTRA_PRODUCT_ID, productId)
                startActivity(intent)
            }

        }
    }

    private fun getAllProducts() {
        viewModel.getAllProduct()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.vpProductImage.unregisterOnPageChangeCallback(pageChangeListener)
    }

}