package com.example.recfilm.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recfilm.databinding.FragmentSelectionsBinding
import com.example.recfilm.utils.AnimationHelper


class SelectionsFragment : Fragment() {
    private val ANIMATORHELPER_POSITION = 4
    private var _binding: FragmentSelectionsBinding? = null
    private val binding: FragmentSelectionsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.selectionsFragmentRoot,
            requireActivity(),
            ANIMATORHELPER_POSITION
        )
    }
}