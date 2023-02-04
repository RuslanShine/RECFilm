package com.example.recfilm.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recfilm.R
import com.example.recfilm.databinding.FragmentSettingsBinding
import com.example.recfilm.utils.AnimationHelper
import com.example.recfilm.viewmodel.SettingsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_selections.*
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(SettingsFragmentViewModel::class.java)
    }
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Подключаем анимации и передаем номер позиции у кнопки в нижнем меню
        AnimationHelper.performFragmentCircularRevealAnimation(binding.settingsFragmentRoot, requireActivity(), 5)
        //Слушаем, какой у нас сейчас выбран вариант в настройках
        viewModel.categoryPropertyLifeData.observe(viewLifecycleOwner, Observer<String> {
            when(it) {
                POPULAR_CATEGORY -> binding.radioGroup.check(R.id.radio_popular)
                TOP_RATED_CATEGORY -> binding.radioGroup.check(R.id.radio_top_rated)
                UPCOMING_CATEGORY -> binding.radioGroup.check(R.id.radio_upcoming)
                NOW_PLAYING_CATEGORY -> binding.radioGroup.check(R.id.radio_now_playing)
            }
        })
        //Слушатель для отправки нового состояния в настройки
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.radio_popular -> viewModel.putCategoryProperty(POPULAR_CATEGORY)
                R.id.radio_top_rated -> viewModel.putCategoryProperty(TOP_RATED_CATEGORY)
                R.id.radio_upcoming -> viewModel.putCategoryProperty(UPCOMING_CATEGORY)
                R.id.radio_now_playing -> viewModel.putCategoryProperty(NOW_PLAYING_CATEGORY)
            }
        }
    }

    companion object {
        private const val POPULAR_CATEGORY = "popular"
        private const val TOP_RATED_CATEGORY = "top_rated"
        private const val UPCOMING_CATEGORY = "upcoming"
        private const val NOW_PLAYING_CATEGORY = "now_playing"
    }
}