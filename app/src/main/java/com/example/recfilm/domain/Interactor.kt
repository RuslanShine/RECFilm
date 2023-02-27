package com.example.recfilm.domain

import androidx.lifecycle.LiveData
import com.example.recfilm.API
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.data.Entity.TmdbResultsDto
import com.example.recfilm.data.MainRepository
import com.example.recfilm.data.TmdbApi
import com.example.recfilm.data.preferenes.PreferenceProvider
import com.example.recfilm.utils.Converter
import com.example.recfilm.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider,
) {

    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbResultsDto> {
                override fun onResponse(
                    call: Call<TmdbResultsDto>,
                    response: Response<TmdbResultsDto>,
                ) {
                    //При успехе вызываем метод, передаём on Success и в этот коллбэк список фильмов
                    val list = Converter.convertApiListDtoList(response.body()?.tmdbFilms)
                    //Кладём фильмы в бд
                    list.forEach {
                        repo.putToDb(list)
                    }
                    callback.onSuccess()
                }

                override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                    //В случае провала вызываем другой метод коллбека
                    callback.onFailure()
                }
            })
    }

    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    //Вызываем метод репозитория другим методом, чтобы он забрал фильмы из БД
    fun getFilmsFromDB(): LiveData<List<Film>> = repo.getAllFromDB()
}

