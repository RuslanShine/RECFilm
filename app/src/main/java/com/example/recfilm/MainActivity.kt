package com.example.recfilm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
val NUMBER_FRAGMENTS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(
                        this,
                        getString(R.string.menu_settings_toast),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    true
                }
                else -> false
            }
        }

        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.favorites -> {
                    Toast.makeText(this, getString(R.string.menu_favorites), Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, getString(R.string.menu_watch_later), Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, getString(R.string.menu_selections), Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                else -> false
            }
        }
    }

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