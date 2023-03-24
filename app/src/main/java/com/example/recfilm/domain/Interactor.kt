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
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Interactor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider,
) {

    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        //Показываем ProgressBar
        progressBarState.onNext(true)
        //Метод getDefaultCategoryFromPreferences() будет получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbResultsDto> {
                override fun onResponse(
                    call: Call<TmdbResultsDto>,
                    response: Response<TmdbResultsDto>,
                ) {
                    //При успехе вызываем метод, передаём on Success и в этот коллбэк список фильмов
                    val list = Converter.convertApiListDtoList(response.body()?.tmdbFilms)
                    //Кладём фильмы в бд и выключаем ProgressBar
                    Completable.fromSingle<List<Film>> {
                        repo.putToDb(list)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                    progressBarState.onNext(false)
                }

                override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                    //В случае провала выключаем ProgressBar
                    progressBarState.onNext(false)
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
    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()
}

