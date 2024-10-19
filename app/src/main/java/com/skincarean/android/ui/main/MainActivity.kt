package com.skincarean.android.ui.main

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import com.skincarean.android.R
import com.skincarean.android.core.data.LoginSharedPref
import com.skincarean.android.databinding.ActivityMainBinding
import com.skincarean.android.ui.home.HomeFragment
import com.skincarean.android.ui.order.OrderFragment
import com.skincarean.android.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private var selectedTab: Int = 1

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LoginSharedPref.checkSession(this)

        supportFragmentManager.beginTransaction().setReorderingAllowed(true)
            .replace(R.id.fragment_container, HomeFragment(), null).commit()

        setupBottomBar()
    }

    private fun setupBottomBar() {
        binding.ivOrder.setImageResource(R.drawable.ic_order)
        binding.ivProfile.setImageResource(R.drawable.ic_profile)
        binding.homeLayout.setOnClickListener {
            if (selectedTab != 1) {
                supportFragmentManager.beginTransaction().setReorderingAllowed(true)
                    .replace(R.id.fragment_container, HomeFragment(), null).commit()


                binding.tvOrder.visibility = View.GONE
                binding.tvProfile.visibility = View.GONE

                binding.layoutOrder.setBackgroundColor(resources.getColor(android.R.color.transparent))
                binding.layoutProfile.setBackgroundColor(resources.getColor(android.R.color.transparent))


                binding.tvHome.visibility = View.VISIBLE
                binding.ivHome.setImageResource(R.drawable.ic_selected_home)

                binding.homeLayout.setBackgroundResource(R.drawable.round_back_100)

                scaleAnimation(binding.homeLayout)

                selectedTab = 1
            }

        }
        binding.layoutOrder.setOnClickListener {
            supportFragmentManager.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.fragment_container, OrderFragment(), null).commit()

            if (selectedTab != 2) {
                binding.tvHome.visibility = View.GONE
                binding.tvProfile.visibility = View.GONE


                binding.ivHome.setImageResource(R.drawable.ic_home)
                binding.ivProfile.setImageResource(R.drawable.ic_profile)

                binding.homeLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                binding.layoutProfile.setBackgroundColor(resources.getColor(android.R.color.transparent))


                binding.tvOrder.visibility = View.VISIBLE
                binding.ivOrder.setImageResource(R.drawable.ic_selected_order)
                binding.layoutOrder.setBackgroundResource(R.drawable.round_back_100)

                scaleAnimation(binding.layoutOrder)
                selectedTab = 2
            }
        }

        binding.layoutProfile.setOnClickListener {
            supportFragmentManager.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.fragment_container, ProfileFragment(), null).commit()
            if (selectedTab != 3) {
                binding.tvOrder.visibility = View.GONE
                binding.tvHome.visibility = View.GONE
                binding.ivOrder.setImageResource(R.drawable.ic_order)
                binding.ivHome.setImageResource(R.drawable.ic_home)



                binding.layoutOrder.setBackgroundColor(resources.getColor(android.R.color.transparent))
                binding.homeLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))

                binding.layoutProfile.setBackgroundResource(R.drawable.round_back_100)
                binding.ivProfile.setImageResource(R.drawable.ic_selected_profile)
                binding.tvProfile.visibility = View.VISIBLE

                scaleAnimation(binding.layoutProfile)
                selectedTab = 3
            }
        }


    }

    private fun scaleAnimation(view: View) {
        val scaleAnimation = ScaleAnimation(
            0.8f,
            1.0f,
            1f,
            1f,
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f
        )
        scaleAnimation.duration = 200
        scaleAnimation.fillAfter = true

        view.startAnimation(scaleAnimation)
    }
}