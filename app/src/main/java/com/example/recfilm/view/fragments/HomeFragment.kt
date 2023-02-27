package com.example.recfilm.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recfilm.databinding.FragmentHomeBinding
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.utils.AnimationHelper
import com.example.recfilm.view.rv_adapters.FilmListRecyclerAdapter
import com.example.recfilm.view.MainActivity
import com.example.recfilm.view.rv_adapters.TopSpacingItemDecoration
import com.example.recfilm.viewmodel.HomeFragmentViewModel
import java.util.*

class HomeFragment : Fragment() {
    private val POSITION_ANIMATION_HELPER = 1
    private val TOP_SPACING_ITEM = 8
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private var filmsDataBase = listOf<Film>()
        set(value) {
            if (field == value) return
            field = value
            filmsAdapter.addItems(field)
        }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.homeFragmentRoot,
            requireActivity(),
            POSITION_ANIMATION_HELPER
        )

        initSearchView()
        initPullToRefresh()
        //находим наш RV
        initRecyckler()
        //Кладем нашу БД в RV
        viewModel.filmsListLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            filmsDataBase = it
            filmsAdapter.addItems(it)
        })
        //подписываемся на изменения в прогресс-баре
        viewModel.showProgressBar.observe(viewLifecycleOwner, androidx.lifecycle.Observer<Boolean>{
            binding.progressBar.isVisible = it
        })
    }

    private fun initPullToRefresh() {
        //Вешаем слушатель, чтобы вызвался pull to refresh
        binding.pullToRefresh.setOnRefreshListener {
            //Чистим адаптер(items нужно будет сделать паблик или создать для этого публичный метод)
            filmsAdapter.items.clear()
            //Делаем новый запрос фильмов на сервер
            viewModel.getFilms()
            //Убираем крутящееся колечко
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun initSearchView() {
        //нажатие на поле "поиск" целиком
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        //Слушатель изменений введенного текста
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Обработка нажатия "поиск" на клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //Обработка кайдого изменения текста
            override fun onQueryTextChange(newText: String): Boolean {
                //Если ввод пуст вставляем всю БД в адаптер
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                //Фильтруем список на подходящие сочетания
                val result = filmsDataBase.filter {
                    //Приводим запрос и ммя фильма к нижнему регистру
                    it.title.toLowerCase(Locale.getDefault())
                        .contains(newText.toLowerCase(Locale.getDefault()))
                }
                //Добавляем в адаптер
                filmsAdapter.addItems(result)
                return true
            }
        })
    }

    private fun initRecyckler() {
        binding.mainRecycler.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(TOP_SPACING_ITEM)
            addItemDecoration(decorator)
        }

    }
}

