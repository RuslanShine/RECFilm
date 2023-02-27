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


    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmsListLiveData: LiveData<List<Film>>

    init {
        App.instance.dagger.inject(this)
        filmsListLiveData= interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(FILM_PAGE_ONE, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {

            }

            //Кладём фильмы из БД в LiveData, чтобы на UI появился список фильмов в отдельном потоке
            override fun onFailure() {

            }
        })
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}
