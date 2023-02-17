package com.example.recfilm.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recfilm.databinding.FragmentFavoritesBinding
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.utils.AnimationHelper
import com.example.recfilm.view.rv_adapters.FilmListRecyclerAdapter
import com.example.recfilm.view.MainActivity
import com.example.recfilm.view.rv_adapters.TopSpacingItemDecoration


class FavoritesFragment : Fragment() {
    private val PADDINGLNDP_DECORATOR = 8
    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding!!

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoritesList: List<Film> = emptyList()

        AnimationHelper.performFragmentCircularRevealAnimation(binding.favoritesFragmentRoot, requireActivity(), 2)

        binding.favoritesRecycler
            .apply {
                filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
                adapter = filmsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                val decorator = TopSpacingItemDecoration(PADDINGLNDP_DECORATOR)
                addItemDecoration(decorator)
             }
        filmsAdapter.addItems(favoritesList)
    }
}