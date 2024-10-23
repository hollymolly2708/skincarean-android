package com.skincarean.android.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skincarean.android.R
import com.skincarean.android.databinding.ActivityDetailTopBrandBinding

class DetailTopBrandActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailTopBrandBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTopBrandBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}