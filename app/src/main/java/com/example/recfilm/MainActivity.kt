package com.example.recfilm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    val filmsDataBase = listOf(
        Film("Interstellar",R.drawable.interstellar,getString(R.string.description_interstellar)),
        Film("The batman",R.drawable.the_batman,getString(R.string.description_batman)),
        Film("The social",R.drawable.the_social,getString(R.string.description_the_social)),
        Film("Star vars",R.drawable.star_vars_poster,getString(R.string.description_star_vars)),
        Film("Spider man",R.drawable.spider_man,getString(R.string.description_spider_man)),
        Film("Ready player one",R.drawable.ready_player_one,getString(R.string.description_ready_player_one)),
        Film("Queens gambit",R.drawable.queens_gambit,getString(R.string.description_queens_gambit)),
        Film("Inception",R.drawable.inception,getString(R.string.description_inception)),
        Film("Anna inventing",R.drawable.anna_inventing,getString(R.string.description_anna_inveting))

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()

        main_recycler.apply {


            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {}
            })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        filmsAdapter.addItems(filmsDataBase)
    }

    private fun initNavigation() {

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, getString(R.string.menu_settings_toast), Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.favorites -> {
                    Toast.makeText(this, getString(R.string.menu_favorites), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, getString(R.string.menu_watch_later), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, getString(R.string.menu_selections), Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}