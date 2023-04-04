package com.example.recfilm.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recfilm.databinding.FragmentHomeBinding
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.utils.AnimationHelper
import com.example.recfilm.utils.AutoDisposable
import com.example.recfilm.utils.addTo
import com.example.recfilm.view.rv_adapters.FilmListRecyclerAdapter
import com.example.recfilm.view.MainActivity
import com.example.recfilm.view.rv_adapters.TopSpacingItemDecoration
import com.example.recfilm.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class HomeFragment : Fragment() {
    private val POSITION_ANIMATION_HELPER = 1
    private val TOP_SPACING_ITEM = 8
    private val TIMEOUT_SEARCH = 800L


    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private val autoDisposable = AutoDisposable()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Привязываемся к ЖЦ компонента
        autoDisposable.bindTo(lifecycle)
        //Фрагмент переживает пересоздание хост-активити
        retainInstance = true
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
            .addTo(autoDisposable)

        viewModel.showProgressBar
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.progressBar.isVisible = it
            }
            .addTo(autoDisposable)
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

        io.reactivex.rxjava3.core.Observable.create(ObservableOnSubscribe<String> { subscriber ->
            //Вешаем слушатель на клавиатуру
            binding.searchView.setOnQueryTextListener(object :
            //Вызывается на ввод символов
                SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    filmsAdapter.items.clear() //очищаем RV-адаптер, готовя место под ответ с сервера
                    subscriber.onNext(newText) //отправляем по потоку поисковой запрос
                    return false
                }

                //Вызывается по нажатию кнопки "Поиск", отправляем поисковой запрос
                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscriber.onNext(query)
                    return false
                }
            })
        })
            //Указываем, в каком планировщике будет выполняться работа
            .subscribeOn(Schedulers.io())
            //Приводим ввод текста для поиска к нижнему регистр
            .map {
                it.toLowerCase(Locale.getDefault()).trim()
            }
            //чтобы наши поисковые запросы срабатывали не на каждое введение символа,
            //чтобы зазря не совершать запросы на сервер
            .debounce(800, TimeUnit.MILLISECONDS)
            //фильтруем результат, если у нас введена пустая строка
            .filter {
                //Если в поиске пустое поле, возвращаем список фильмов по умолчанию
                viewModel.getFilms()
                it.isNotBlank()
            }
            //Переходим в другой Observable
            .flatMap {
                viewModel.getSearchResult(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            //метод позволяет в параметры метода передать лямбды, которые нужны
            .subscribeBy(
                onError = {
                    Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT)
                        .show()
                },
                onNext = {
                    filmsAdapter.addItems(it)
                }
            )
            //Отписываемся по окончанию
            .addTo(autoDisposable)
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

