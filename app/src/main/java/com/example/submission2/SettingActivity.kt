package com.example.submission2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.submission2.Reminder.SettingFragment

class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        fragmentManager.beginTransaction().replace(R.id.fragment_container, SettingFragment())
            .commit()

      //  setFragment(SettingFragment(),"s")
    }

    private fun setFragment(fragment: Fragment, title: String) {
        this.title = title
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
