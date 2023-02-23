package com.example.recfilm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recfilm.App
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.domain.Interactor
import java.util.concurrent.Executors
import javax.inject.Inject

const val FILM_PAGE_ONE = 1

class HomeFragmentViewModel : ViewModel() {
    private val _filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    val filmsListLiveData: LiveData<List<Film>>
        get() = _filmsListLiveData

    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(FILM_PAGE_ONE, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                _filmsListLiveData.postValue(films)
            }

            //Кладём фильмы из БД в LiveData, чтобы на UI появился список фильмов в отдельном потоке
            override fun onFailure() {
                Executors.newSingleThreadExecutor().execute {
                    _filmsListLiveData.postValue(interactor.getFilmsFromDB())
                }
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}
