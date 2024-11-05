package com.skincarean.android.ui.profile.update_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.skincarean.android.Utilities
import com.skincarean.android.di.AppInjector
import com.skincarean.android.databinding.ActivityUpdateProfileBinding
import com.skincarean.android.ui.profile.ProfileViewModel

class UpdateProfileActivity : AppCompatActivity() {


    companion object {
        const val EXTRA_UPDATE_PROFILE_NUMBER = "extra_update_profile_number"
        const val EXTRA_UPDATE_PROFILE_NAME = "extra_update_profile_name"
        const val EXTRA_UPDATE_PROFILE_ADDRESS = "extra_update_profile_address"
        const val EXTRA_UPDATE_PROFILE_EMAIL = "extra_update_profile_email"
        const val EXTRA_UPDATE_PROFILE_PHONE = "extra_update_profile_phone"
    }

    private lateinit var binding: ActivityUpdateProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = AppInjector.provideViewModelFactory()
        profileViewModel = ViewModelProvider(this, factory = factory)[ProfileViewModel::class.java]

        val getNumberExtra = intent.getIntExtra(EXTRA_UPDATE_PROFILE_NUMBER, 0)
        val getNameExtra = intent.getStringExtra(EXTRA_UPDATE_PROFILE_NAME) ?: ""
        val getAddressExtra = intent.getStringExtra(EXTRA_UPDATE_PROFILE_ADDRESS) ?: ""
        val getEmailExtra = intent.getStringExtra(EXTRA_UPDATE_PROFILE_EMAIL) ?: ""
        val getPhoneExtra = intent.getStringExtra(EXTRA_UPDATE_PROFILE_PHONE) ?: ""

        visibilityUI(
            getNumberExtra,
            getNameExtra,
            getEmailExtra,
            getPhoneExtra,
            getAddressExtra
        )
        setupObservers()

    }

    private fun setupObservers() {
        profileViewModel.message.observe(this) { event ->
            event.getContentIfNotHandled()?.let { Utilities.customDialog(it, this) }

        }
    }

    private fun visibilityUI(
        value: Int,
        name: String,
        email: String,
        phone: String,
        address: String,
    ) {
        val edtName = binding.edtName
        val edtAddress = binding.edtAddress
        val edtPhone = binding.edtPhone
        val edtEmail = binding.edtEmail
        when (value) {
            1 -> {
                edtName.visibility = View.VISIBLE
                edtAddress.visibility = View.GONE
                edtEmail.visibility = View.GONE
                edtPhone.visibility = View.GONE

                edtName.setText(name)
                binding.btnSave.setOnClickListener {
                    profileViewModel.updateUser(edtName.text.toString(), address, phone, email)
                    finish()
                }

            }

            2 -> {
                edtName.visibility = View.GONE
                edtAddress.visibility = View.VISIBLE
                edtEmail.visibility = View.GONE
                edtPhone.visibility = View.GONE

                edtAddress.setText(address)

                binding.btnSave.setOnClickListener {
                    profileViewModel.updateUser(name, edtAddress.text.toString(), phone, email)
                    finish()
                }
            }

            3 -> {
                edtName.visibility = View.GONE
                edtAddress.visibility = View.GONE
                edtEmail.visibility = View.VISIBLE
                edtPhone.visibility = View.GONE
                edtEmail.setText(email)
                binding.btnSave.setOnClickListener {
                    profileViewModel.updateUser(name, address, phone, edtEmail.text.toString())
                    finish()
                }
            }

            4 -> {
                edtName.visibility = View.GONE
                edtAddress.visibility = View.GONE
                edtEmail.visibility = View.GONE
                edtPhone.visibility = View.VISIBLE
                edtPhone.setText(phone)
                binding.btnSave.setOnClickListener {
                    profileViewModel.updateUser(name, address, edtPhone.text.toString(), email)
                    finish()
                }

            }
        }


    }
}