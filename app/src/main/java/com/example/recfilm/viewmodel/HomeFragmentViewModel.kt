package com.example.recfilm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recfilm.App
import com.example.recfilm.domain.Film
import com.example.recfilm.domain.Interactor

class HomeFragmentViewModel : ViewModel() {
    private val _filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    val filmsListLiveData: LiveData<List<Film>>
        get() = _filmsListLiveData
    private var interactor: Interactor = App.instance.interactor

    init {
        val films = interactor.getFilmsFromApi(1, object : ApiCallback{
            override fun onSuccess(films: List<Film>){
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
