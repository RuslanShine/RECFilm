package com.example.recfilm.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recfilm.databinding.FragmentWatchLaterBinding
import com.example.recfilm.utils.AnimationHelper

class WatchLaterFragment : Fragment() {
    private var _binding: FragmentWatchLaterBinding? = null
    private val binding: FragmentWatchLaterBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWatchLaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.watchLaterFragmentRoot,
            requireActivity(),
            3
        )
    }
}