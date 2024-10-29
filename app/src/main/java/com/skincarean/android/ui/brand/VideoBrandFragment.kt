package com.skincarean.android.ui.brand

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skincarean.android.R
import com.skincarean.android.databinding.FragmentArticleBrandBinding
import com.skincarean.android.databinding.FragmentVideoBrandBinding

class VideoBrandFragment : Fragment() {

    private var _binding: FragmentVideoBrandBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVideoBrandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}