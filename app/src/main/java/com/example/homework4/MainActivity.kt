package com.example.homework4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
private var FAVORITE_FRAGMENT = FavoriteFragment().javaClass.name
private var STAR_FRAGMENT = StarFragment().javaClass.name
private const val LAST_SELECTED_ITEM = "LAST_SELECTED_ITEM"

class MainActivity : AppCompatActivity() {
    private var favoriteFragment = FavoriteFragment()
    private var starFragment = StarFragment()
    private lateinit var bottomNavigationMenu: BottomNavigationView
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            bottomNavigationMenu = findViewById(R.id.bottom_navigator)

            //натсроим клики по элементам нижней навигации
            bottomNavigationMenu.setOnItemSelectedListener { item ->
                var fragment: Fragment? = null
                when (item.itemId) {
                    R.id.list1 -> {
                        fragment =
                            savedInstanceState?.let {
                                supportFragmentManager.getFragment(it, FAVORITE_FRAGMENT)
                            } ?: favoriteFragment
                    }
                    R.id.list2 -> {
                        fragment =
                            savedInstanceState?.let {
                                supportFragmentManager.getFragment(it, STAR_FRAGMENT)
                            } ?: starFragment
                    }
                }
                replaceFragment(fragment!!)
                true
            }

            bottomNavigationMenu.selectedItemId =
                savedInstanceState?.getInt(LAST_SELECTED_ITEM) ?: R.id.list1

        }

        override fun onSaveInstanceState(outState: Bundle) {
            outState.putInt(LAST_SELECTED_ITEM, bottomNavigationMenu.selectedItemId)

            val fragment = supportFragmentManager.fragments.last()
            supportFragmentManager.putFragment(outState, fragment.javaClass.name, fragment)
            super.onSaveInstanceState(outState)
        }

        private fun replaceFragment(fragment: Fragment) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_view, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
