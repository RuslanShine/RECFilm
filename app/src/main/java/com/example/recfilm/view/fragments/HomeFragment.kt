package com.example.recfilm.view.fragments

import android.content.Context
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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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
        //Используем backing field
        set(value) {
            //Если придет такое же значение то мы выходим из метода
            if (field == value) return
            //Если прило другое значение, то кладем его в переменную
            field = value
            //Обновляем RV адаптер
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
        viewModel.filmsListData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                filmsAdapter.addItems(list)
                filmsDataBase = list
            }

        viewModel.showProgressBar
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.progressBar.isVisible = it
            }
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

