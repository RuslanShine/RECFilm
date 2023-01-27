package com.example.recfilm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recfilm.domain.Film
import com.example.recfilm.domain.Interactor
import org.koin.core.KoinComponent
import org.koin.core.inject


class HomeFragmentViewModel : ViewModel(), KoinComponent {
    private val _filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    val filmsListLiveData: LiveData<List<Film>>
        get() = _filmsListLiveData

    //Инициализируем интерактор
    private val interactor: Interactor by inject()

    init {
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                _filmsListLiveData.postValue(films)
            }

            override fun onFailure() {
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}
