package com.example.recfilm.domain

import com.example.recfilm.API
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.data.MainRepository
import com.example.recfilm.data.preferenes.PreferenceProvider
import com.example.recfilm.utils.Converter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val PAGE_ONE = 1

class Interactor(
    private val repo: MainRepository,
    private val retrofitService: com.example.remote_module.TmdbApi,
    private val preferences: PreferenceProvider,
) {

    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        //Показываем ProgressBar
        progressBarState.onNext(true)
        //Метод getDefaultCategoryFromPreferences() будет получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiListDtoList(it.tmdbFilms)
            }
            .subscribeBy(
                onError = {
                    progressBarState.onNext(false)
                },
                onNext = {
                    progressBarState.onNext(false)
                    repo.putToDb(it)
                }
            )
    }

    //метод запроса для поиска, возвращает observable
    fun getSearchResultFromApi(search: String): Observable<List<Film>> =
        retrofitService.getFilmFromSearch(API.KEY, "ru-RU", search, PAGE_ONE)
            .map {
                Converter.convertApiListDtoList(it.tmdbFilms) //map, чтобы конвертировать ответ от сервера в DTO-объект
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

