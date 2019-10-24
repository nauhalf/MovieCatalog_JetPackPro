package com.dicoding.naufal.moviecatalogue.testing

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.dicoding.naufal.moviecatalogue.R

class SingleFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER
            )
            id = R.id.container
        }
        setContentView(content)
    }

    fun setFragment(fragment: Fragment){
        supportFragmentManager.commit(false) {
            add(R.id.container, fragment, "TEST")
        }
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.commit(false) {
            add(R.id.container, fragment)
        }
    }
}