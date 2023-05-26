package com.example.recfilm.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.recfilm.App
import com.example.recfilm.R
import com.example.recfilm.databinding.ActivityMainBinding
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.receivers.ConnectionChecker
import com.example.recfilm.view.fragments.*
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class MainActivity : AppCompatActivity() {
    private val NUMBER_FRAGMENTS = 1
    private lateinit var binding: ActivityMainBinding
    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()

        //BroadcastReceive слушает низкий уроввень батареи и подключение кабеля зарядки
        //инициальзировали переменную
        receiver = ConnectionChecker()
        //добавляем фильтры
        val filters = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        //Регистрируем receiver
        registerReceiver(receiver,filters)

        //Remote Config
        if (!App.instance.isPromoShown) {
            //Получаем доступ к Remote Config
            val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            //Устанавливаем настройки
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
            firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
            //Вызываем метод, который получит данные с сервера и вешаем слушатель
            firebaseRemoteConfig.fetch()
                .addOnCompleteListener {
                    //Если все получилось успешно
                    if (it.isSuccessful) {
                        //активируем последний полученный конфиг с сервера
                        firebaseRemoteConfig.activate()
                        //Получаем ссылку
                        val filmLink = firebaseRemoteConfig.getString("film_link")
                        //Если поле не пустое
                        if (filmLink.isNotBlank
                                ()) {
                            //Ставим флаг, что уже промо показали
                            App.instance.isPromoShown = true
                            //Включаем промо верстку
                            binding.promoViewGroup.apply {
                                //Делаем видимой
                                visibility = View.VISIBLE
                                //Анимируем появление
                                animate()
                                    .setDuration(1500)
                                    .alpha(1f)
                                    .start()
                                //Вызываем метод, который загрузит постер в ImageView
                                setLinkForPoster(filmLink)
                                //Кнопка, по нажатии на которую промо уберется (желательно сделать отдельную кнопку с крестиком)
                                watchButton.setOnClickListener {
                                    visibility = View.GONE
                                }
                            }
                        }
                    }
                }
        }
    }
    //Отключаем receiver
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initNavigation() {

        binding.bottomNavigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: HomeFragment(), tag)
                    true
                }
                R.id.favorites -> {
                    Toast.makeText(this, "Опция доступна в платной версии", Toast.LENGTH_SHORT).show()
                    false
                }
                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: WatchLaterFragment(), tag)
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, "Опция доступна в платной версии", Toast.LENGTH_SHORT).show()
                    false
                }
                R.id.settings -> {
                    val tag = "settings"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: SettingsFragment(), tag)
                    true
                }
                else -> false
            }
        }

//        binding.topAppBar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.settings -> {
//                    Toast.makeText(
//                        this,
//                        getString(R.string.menu_settings_toast),
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                    true
//                }
//                else -> false
//            }
//        }
    }

    //Ищем фрагмент по тэгу, если он есть то возвращаем его, если нет - то null
    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
//            .addToBackStack(null)                                      //фрагменты нижнего бара
            .commit()
    }

    //Запрос на выход из приложения
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == NUMBER_FRAGMENTS) {
            AlertDialog.Builder(this)
                .setTitle(R.string.text_Dialog)
                .setIcon(R.drawable.ic_round_collections)
                .setPositiveButton(R.string.text_Dialog_yes) { _, _ ->
                    finish()
                }
                .setNegativeButton(R.string.text_Dialog_no) { _, _ ->

                }
                .show()
        } else {
            super.onBackPressed()
        }
    }
}