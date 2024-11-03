package com.skincarean.android.ui.product.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.skincarean.android.OnItemClickCallback
import com.skincarean.android.R
import com.skincarean.android.Utilities
import com.skincarean.android.di.Injector
import com.skincarean.android.core.data.domain.model.product.DetailProduct
import com.skincarean.android.core.data.domain.model.product.Product
import com.skincarean.android.core.data.domain.model.product.ProductImageItem
import com.skincarean.android.core.data.domain.model.product.ProductVariant
import com.skincarean.android.core.data.source.remote.request.CartRequest
import com.skincarean.android.databinding.ActivityDetailProductBinding
import com.skincarean.android.ui.cart.CartActivity
import com.skincarean.android.ui.cart.CartViewModel
import com.skincarean.android.ui.checkout.DirectlyCheckoutActivity
import com.skincarean.android.ui.home.ProductAdapter
import okhttp3.OkHttpClient
import java.math.BigDecimal

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private var pageChangeListener: ViewPager2.OnPageChangeCallback? = null
    private var quantity: Int = 0

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply { setMargins(8, 0, 8, 0) }

    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        intent.getStringExtra(EXTRA_PRODUCT_ID)?.let { productId ->
            getProductById(productId)
            sendOrder(productId)

            binding.btnCart.setOnClickListener {
                showAddToCartDialog(productId)
            }
        }

        setupObservers()
        getAllProducts()
        bindingTabLayout()
        quantityCount()
        setupCartAndBackButtons()

    }

    private fun setupViewModel() {
        val factory = Injector.provideViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
    }

    private fun showAddToCartDialog(productId: String) {
        AlertDialog.Builder(this)
            .setTitle("Tambah produk")
            .setMessage("Apakah anda ingin menambahkan produk kedalam keranjang?")
            .setPositiveButton("OK") { _, _ ->
                viewModel.variantId.value?.let { variantId ->
                    val cartRequest = CartRequest(productId, 1, variantId)
                    cartViewModel.addProductToCart(cartRequest)
                }
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun bindingTabLayout() {
        val detailProductTabLayoutAdapter = DetailProductTabLayoutAdapter(this)
        binding.viewpagerDetailProduct.adapter = detailProductTabLayoutAdapter

        val tabNames = arrayOf("Description", "Details")
        TabLayoutMediator(
            binding.tabLayoutDetailProduct,
            binding.viewpagerDetailProduct
        ) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    private fun setupCartAndBackButtons() {
        binding.ivBack.setOnClickListener { finish() }
        binding.ivCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun getProductById(productId: String) {
        viewModel.getProductByProductId(productId)
    }

    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.message.observe(this) { Utilities.customDialog(it, this) }

        viewModel.detailProduct.observe(this) { detailProduct ->
            detailProduct?.let {
                setupFirstDetailProduct(it)
                setupListVariantProduct(it.productVariants)
            }
        }

        viewModel.listProduct.observe(this) { products ->
            setupRecyclerViewMaybeULike(products)
        }
    }

    private fun setupFirstDetailProduct(detailProduct: DetailProduct) {
        binding.apply {
            tvInputNameProduct.text = detailProduct.productName
            tvInputStok.text = detailProduct.totalStok.toString()
            tvInputCategory.text = detailProduct.categoryName
        }
        hideVariant(detailProduct)


        // Set the selected variant if it exists
        detailProduct.productVariants?.firstOrNull()?.let { variant ->
            variant.isSelected = true
            viewModel.setVariantId(variant.id)
            setupProductImages(variant.productImageItems)

            // Call the price setup here to ensure it uses the selected variant
            setupViewPriceWithDiscount(detailProduct.copy(productVariant = variant))
        }

        // You may want to handle the case where there are no variants as well
        if (detailProduct.productVariants.isNullOrEmpty()) {
            // Handle the no variant case appropriately
            setupViewPriceWithDiscount(detailProduct)
        }
    }


    private fun setupListVariantProduct(listProductVariant: List<ProductVariant?>?) {
        val productVariantAdapter = ProductVariantAdapter(listProductVariant)
        binding.rvProductVarian.apply {
            adapter = productVariantAdapter
            layoutManager = LinearLayoutManager(
                this@DetailProductActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }

        productVariantAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onProductVariantClickCallback(data: ProductVariant) {
                updateDetailProductByVariant(data)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateDetailProductByVariant(productVariant: ProductVariant) {
        // Dapatkan detail produk saat ini dari ViewModel atau sumber lain
        val detailProduct = viewModel.detailProduct

        // Perbarui tampilan dengan harga baru dari productVariant
        binding.apply {
            tvInputTotalPrice.text = Utilities.numberFormat(productVariant.price)
            tvInputOriginalPrice.text = Utilities.numberFormat(productVariant.originalPrice)
            tvInputDiscount.text = "${productVariant.discount}%"
        }

        // Set variant ID
        viewModel.setVariantId(productVariant.id)

        // Perbarui gambar produk
        setupProductImages(productVariant.productImageItems)

        // Panggil setupViewPriceWithDiscount dengan salinan detailProduct yang baru
        setupViewPriceWithDiscount(detailProduct.value?.copy(productVariant = productVariant))
    }

    private fun hideVariant(detailProduct: DetailProduct) {
        binding.layoutVariant.visibility =
            if (detailProduct.productVariants.isNullOrEmpty() || detailProduct.productVariants!!.size <= 1) {
                View.GONE
            } else {
                View.VISIBLE
            }
    }


    @SuppressLint("SetTextI18n")
    private fun setupViewPriceWithDiscount(detailProduct: DetailProduct?) {
        val tvOriginalPrice = binding.tvInputOriginalPrice
        val tvDiscount = binding.tvInputDiscount
        val tvTotalPrice = binding.tvInputTotalPrice

        // Check if there's a discount and if it's greater than 0
        val discount = detailProduct?.productVariant?.discount
        if ((discount?.compareTo(BigDecimal.ZERO) ?: -1) > 0) {
            // Show and style the discount and original price
            tvDiscount.visibility = View.VISIBLE
            tvOriginalPrice.visibility = View.VISIBLE
            tvTotalPrice.setTextColor(ContextCompat.getColor(this, R.color.red))

            // Strike-through the original price and format text style
            tvOriginalPrice.paintFlags = tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvOriginalPrice.setTypeface(tvOriginalPrice.typeface, Typeface.BOLD_ITALIC)
            tvOriginalPrice.textSize = 10f

            // Set discount and price values
            tvDiscount.text = "${discount}%"
            tvOriginalPrice.text =
                Utilities.numberFormat(detailProduct?.productVariant?.originalPrice)
            tvTotalPrice.text = Utilities.numberFormat(detailProduct?.productVariant?.price)
        } else {
            // No discount: hide discount and original price, reset styling for total price
            tvDiscount.visibility = View.GONE
            tvOriginalPrice.visibility = View.GONE
            tvTotalPrice.setTextColor(ContextCompat.getColor(this, R.color.black))
            tvTotalPrice.paintFlags = 0  // Remove any strike-through

            // Set only the total price without discount styling
            tvTotalPrice.text =
                Utilities.numberFormat(detailProduct?.productVariant?.price ?: BigDecimal.ZERO)
        }


    }

    private fun setupProductImages(productImageItems: List<ProductImageItem?>?) {
        val imageAdapter = ProductItemImageAdapter()
        binding.vpProductImage.adapter = imageAdapter
        binding.slideDotLL.removeAllViews()

        imageAdapter.submitList(productImageItems)
        productImageItems?.let {
            val dotsImage = Array(it.size) { ImageView(this) }
            dotsImage.forEach { dot ->
                dot.setImageResource(R.drawable.non_active_dot)
                binding.slideDotLL.addView(dot, params)
            }
            dotsImage[0].setImageResource(R.drawable.active_dot)

            pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    dotsImage.forEachIndexed { index, imageView ->
                        imageView.setImageResource(if (position == index) R.drawable.active_dot else R.drawable.non_active_dot)
                    }
                }
            }
            binding.vpProductImage.registerOnPageChangeCallback(pageChangeListener!!)
        }
    }

    private fun setupRecyclerViewMaybeULike(products: List<Product>) {
        val adapter = ProductAdapter()
        binding.rvMaybeULike.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(
                this@DetailProductActivity,
                2,
                GridLayoutManager.HORIZONTAL,
                false
            )
        }

        adapter.submitList(products.shuffled())
        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onProductClickCallback(data: Product) {
                Intent(this@DetailProductActivity, DetailProductActivity::class.java).apply {
                    putExtra(EXTRA_PRODUCT_ID, data.productId)
                    startActivity(this)
                }
            }
        })
    }

    private fun quantityCount() {
        binding.tvInputQuantity.text = quantity.toString()
        binding.btnMinus.isEnabled = quantity > 0
        binding.btnPlus.setOnClickListener {
            quantity += 1
            binding.tvInputQuantity.text = quantity.toString()
            binding.btnMinus.isEnabled = true
        }
        binding.btnMinus.setOnClickListener {
            quantity = (quantity - 1).coerceAtLeast(0)
            binding.tvInputQuantity.text = quantity.toString()
            binding.btnMinus.isEnabled = quantity > 0
        }
    }

    private fun sendOrder(productId: String) {
        val stok = 20
        binding.btnToOrder.setOnClickListener {
            var isFailed = false
            when {
                quantity == 0 -> {
                    Utilities.customDialog("Quantity tidak boleh kosong", this)
                    isFailed = true
                }

                quantity > stok -> {
                    Utilities.customDialog("Jumlah yang dipesan tidak boleh lebih dari Stok", this)
                    isFailed = true
                }
            }
            if (!isFailed) {
                Intent(this, DirectlyCheckoutActivity::class.java).apply {
                    putExtra(DirectlyCheckoutActivity.EXTRA_QUANTITY, quantity)
                    putExtra(DirectlyCheckoutActivity.EXTRA_PRODUCT_ID, productId)
                    putExtra(
                        DirectlyCheckoutActivity.EXTRA_PRODUCT_VARIANT_ID,
                        viewModel.variantId.value
                    )
                    startActivity(this)
                }
            }
        }
    }

    private fun getAllProducts() {
        viewModel.getAllProduct()
    }

    override fun onDestroy() {
        super.onDestroy()

        pageChangeListener?.let { binding.vpProductImage.unregisterOnPageChangeCallback(it) }
    }
}
