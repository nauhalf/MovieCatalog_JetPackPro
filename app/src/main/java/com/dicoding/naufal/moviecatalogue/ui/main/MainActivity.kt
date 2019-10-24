package com.dicoding.naufal.moviecatalogue.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.naufal.moviecatalogue.R
import com.dicoding.naufal.moviecatalogue.ui.main.discovery.DiscoveryFragment
import com.dicoding.naufal.moviecatalogue.ui.main.fav.FavoriteFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.template_toolbar.*


class MainActivity : AppCompatActivity() {


    private var mCurrentFragment: Fragment? = DiscoveryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_main, mCurrentFragment!!)
                .commit()
        } else {
            mCurrentFragment = supportFragmentManager.getFragment(savedInstanceState, "FRAGMENT")
            supportFragmentManager.beginTransaction().replace(R.id.frame_main, mCurrentFragment!!).commit()

        }
        setUp()
    }

    private fun setUp() {
        setSupportActionBar(toolbar)
        bottom_nav_main.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_discovery -> {
                    mCurrentFragment = DiscoveryFragment()
                }

                R.id.menu_favorite -> {
                    mCurrentFragment = FavoriteFragment()

                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_main, mCurrentFragment!!)
                .commit()
            true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_setting -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        supportFragmentManager.putFragment(outState, "FRAGMENT", mCurrentFragment!!)
    }
}