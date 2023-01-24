package com.example.recfilm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.recfilm.R
import com.example.recfilm.databinding.ActivityMainBinding
import com.example.recfilm.domain.Film
import com.example.recfilm.view.fragments.*


class MainActivity : AppCompatActivity() {
    private val NUMBER_FRAGMENTS = 1
    private lateinit var binding: ActivityMainBinding

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
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: FavoritesFragment(), tag)
                    true
                }
                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: WatchLaterFragment(), tag)
                    true
                }
                R.id.selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: SelectionsFragment(), tag)
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
    private fun checkFragmentExistence(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
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