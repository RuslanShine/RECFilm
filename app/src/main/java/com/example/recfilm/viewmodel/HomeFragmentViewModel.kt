package com.example.recfilm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recfilm.App
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.domain.Interactor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

import javax.inject.Inject

const val FILM_PAGE_ONE = 1

class HomeFragmentViewModel : ViewModel() {

    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmsListData: Observable<List<Film>>

    //Поле для хранения показа прогресс-бара
    val showProgressBar: BehaviorSubject<Boolean>

    init {
        App.instance.dagger.inject(this)
        showProgressBar = interactor.progressBarState
        filmsListData = interactor.getFilmsFromDB()
        getFilms()
    }

    //показ прогресс-бара
    fun getFilms() {
        interactor.getFilmsFromApi(FILM_PAGE_ONE)
    }

    //поиск
    fun getSearchResult(search: String) = interactor.getSearchResultFromApi(search)
}
