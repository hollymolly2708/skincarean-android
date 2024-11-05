package com.skincarean.android.ui.brand

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.skincarean.android.di.AppInjector
import com.skincarean.android.core.data.domain.model.brand.DetailBrand
import com.skincarean.android.databinding.ActivityDetailBrandBinding

class DetailBrandActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBrandBinding
    private lateinit var brandViewModel: BrandViewModel

    companion object {
        const val EXTRA_BRAND_ID = "extra_brand_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBrandBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        val brandId = intent.getLongExtra(EXTRA_BRAND_ID, 1)

        brandViewModel.selectedBrandId = brandId



        Log.e("DetailBrandActivity", brandId.toString())
        getDetailBrand(brandId)
        setupObservers()
        setupTabAdapter()
    }

    private fun setupObservers() {
        brandViewModel.detailBrand.observe(this) {
            setupUI(it)
            brandViewModel.brandDescription = it.description
            brandViewModel.brandTitle = it.name
        }

    }

    private fun setupUI(brand: DetailBrand) {
        val brandPoster = Uri.parse(brand.brandPoster)
        val brandLogo = Uri.parse(brand.brandLogo)

        Glide.with(this)
            .load(brandPoster)
            .timeout(60000)
            .into(binding.ivBrandPoster)
        Glide.with(this)
            .load(brandLogo)
            .timeout(60000)
            .into(binding.ivBrandLogo)
//        binding.tvTitleBrand.text = brand.name
    }

    private fun setupTabAdapter() {
        val tabAdapter = DetailBrandTabAdapter(this)
        val tabs = binding.tabDetailBrand
        val viewPager = binding.vpDetailBrand
        viewPager.adapter = tabAdapter
        val tabNames = arrayOf("Product", "Article", "Video", "About")
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    private fun setupViewModel() {
        val factory = AppInjector.provideViewModelFactory()
        brandViewModel = ViewModelProvider(this, factory)[BrandViewModel::class.java]
    }

    private fun getDetailBrand(brandId: Long) {
        brandViewModel.getDetailBrandByBrand(brandId)
    }


}