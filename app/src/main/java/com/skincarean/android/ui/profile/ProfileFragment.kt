package com.skincarean.android.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.skincarean.android.R
import com.skincarean.android.core.data.LoginSharedPref
import com.skincarean.android.core.data.di.Injector
import com.skincarean.android.databinding.FragmentProfileBinding
import com.skincarean.android.ui.profile.update_profile.UpdateProfileActivity


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = Injector.provideViewModelFactory()
        profileViewModel =
            ViewModelProvider(requireActivity(), factory)[ProfileViewModel::class.java]

        setUpObservers()
        getCurrentUser()
        wannaUpdate()
        wannaLogout()
    }

    private fun setUpObservers() {
        profileViewModel.currentUser.observe(viewLifecycleOwner) { currentUser ->
            if (currentUser != null) {
                if (currentUser.profilePicture.isNullOrEmpty()) {
                    Glide.with(requireContext())
                        .load(resources.getDrawable(R.drawable.skintific))
                        .circleCrop()
                        .timeout(60000)
                        .into(binding.ivProfile)
                } else {
                    val uri = Uri.parse(currentUser.profilePicture)
                    Glide.with(requireContext())
                        .load(uri)
                        .timeout(60000)
                        .circleCrop()
                        .into(binding.ivProfile)

                    binding.tvInputName.text = currentUser.fullName
                    binding.tvInputEmail.text = currentUser.email
                    binding.tvInputAddress.text = currentUser.address
                    binding.tvInputPhone.text = currentUser.phone
                }


            }

        }

        profileViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            loading(isLoading)
        }
    }

    private fun wannaLogout() {
        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
            LoginSharedPref.clear(requireActivity())

        }
    }

    private fun wannaUpdate() {
        binding.layoutProfileName.setOnClickListener {
            intentToUpdateProfileActivity(1)
        }
        binding.layoutProfileAddress.setOnClickListener {
            intentToUpdateProfileActivity(2)
        }
        binding.layoutProfileEmail.setOnClickListener {
            intentToUpdateProfileActivity(3)
        }
        binding.layoutProfilePhone.setOnClickListener {
            intentToUpdateProfileActivity(4)
        }
    }

    private fun intentToUpdateProfileActivity(number: Int) {
        val intent = Intent(requireActivity(), UpdateProfileActivity::class.java)


        intent.putExtra(
            UpdateProfileActivity.EXTRA_UPDATE_PROFILE_NAME,
            binding.tvInputName.text.toString()
        )
        intent.putExtra(
            UpdateProfileActivity.EXTRA_UPDATE_PROFILE_ADDRESS,
            binding.tvInputAddress.text.toString()
        )
        intent.putExtra(
            UpdateProfileActivity.EXTRA_UPDATE_PROFILE_EMAIL,
            binding.tvInputEmail.text.toString()
        )
        intent.putExtra(
            UpdateProfileActivity.EXTRA_UPDATE_PROFILE_PHONE,
            binding.tvInputPhone.text.toString()
        )


        intent.putExtra(UpdateProfileActivity.EXTRA_UPDATE_PROFILE_NUMBER, number)
        startActivity(intent)
    }


    private fun getCurrentUser() {
        profileViewModel.getCurrentUser()
    }




    private fun loading(loading: Boolean) {
        if (loading) {
            binding.layoutProfileName.visibility = View.GONE
            binding.layoutProfilePhone.visibility = View.GONE
            binding.layoutProfileEmail.visibility = View.GONE
            binding.layoutProfileAddress.visibility = View.GONE
            binding.layoutProfileImage.visibility = View.GONE
            binding.ivLoading.visibility = View.VISIBLE
        } else {
            binding.layoutProfileName.visibility = View.VISIBLE
            binding.layoutProfilePhone.visibility = View.VISIBLE
            binding.layoutProfileEmail.visibility = View.VISIBLE
            binding.layoutProfileAddress.visibility = View.VISIBLE
            binding.layoutProfileImage.visibility = View.VISIBLE
            binding.ivLoading.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getCurrentUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}