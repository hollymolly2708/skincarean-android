package com.skincarean.android.ui.product.detail

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
    private var pageChangeListener: ViewPager2.OnPageChangeCallback? = null
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
                AlertDialog.Builder(this).setTitle("Tambah produk")
                    .setMessage("Apakah anda ingin menambahkan produk kedalam keranjang ?")
                    .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            val cartRequest = CartRequest(productId, 1)
                            cartViewModel.addProductToCart(cartRequest)

                        }

                    }).setNegativeButton("Batal", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            p0?.dismiss()
                        }


                    })

                    .show()


            }
        }

        setupObservers()
        getAllProducts()
        quantityCount()
        bindingButtonCartAndBack()


    }

    private fun bindingButtonCartAndBack() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
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

                val originalPrice = binding.tvInputOriginalPrice
                val discount = binding.tvInputDiscount
                val totalPrice = binding.tvInputTotalPrice


                if (data.isPromo == false) {
                    discount.visibility = View.GONE
                    originalPrice.visibility = View.GONE
                    totalPrice.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.black
                        )
                    )
                }

                originalPrice.paintFlags = originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                originalPrice.setTypeface(originalPrice.typeface, Typeface.BOLD_ITALIC)
                originalPrice.textSize = 10f

                binding.apply {
                    tvInputNameProduct.text = data.productName
                    tvInputStok.text = data.stok.toString()
                    tvInputCategory.text = data.categoryName
                    tvInputOriginalPrice.text =
                        Utilities.numberFormat(data.originalPrice)
                    tvInputCategory.text = data.categoryName
                    tvInputDiscount.text = "${data.discount.toString()}%"
                    tvInputTotalPrice.text = Utilities.numberFormat(data.price)
                }


                if (data.productImage != null) {
                    // Deklarasi adapter untuk product image
                    val imageAdapter = ProductItemImageAdapter()

                    // ViewPager menggunakan imageAdapter
                    binding.vpProductImage.adapter = imageAdapter

                    // Memasukkan productImage ke dalam adapter yang bertipe list
                    imageAdapter.submitList(data.productImage)

                    // Mendeklarasikan dotsImage sebagai array dengan ukuran sesuai jumlah productImage
                    val dotsImage = Array(data.productImage.size) { ImageView(this) }

                    // Looping untuk set gambar non-active dot dan menambahkannya ke slideDotLL
                    dotsImage.forEach { dot ->
                        dot.setImageResource(R.drawable.non_active_dot)
                        binding.slideDotLL.addView(dot, params)
                    }

                    // Mengatur dot pertama sebagai active dot
                    dotsImage[0].setImageResource(R.drawable.active_dot)

                    // Callback untuk mendeteksi perubahan halaman pada ViewPager
                    pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            dotsImage.forEachIndexed { index, imageView ->
                                // Set dot sesuai dengan posisi ViewPager
                                if (position == index) {
                                    imageView.setImageResource(R.drawable.active_dot)
                                } else {
                                    imageView.setImageResource(R.drawable.non_active_dot)
                                }
                            }
                            super.onPageSelected(position)
                        }
                    }

                    // Menambahkan pageChangeListener ke ViewPager

                    binding.vpProductImage.registerOnPageChangeCallback(pageChangeListener!!)


                }


            }
        }


        viewModel.listProduct.observe(this) { data ->
            val adapter = ProductAdapter()
            adapter.submitList(data.shuffled())
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

        //menghapus listener untuk viewpager productImage
        if (pageChangeListener != null) {
            binding.vpProductImage.unregisterOnPageChangeCallback(pageChangeListener!!)
        }

    }

}