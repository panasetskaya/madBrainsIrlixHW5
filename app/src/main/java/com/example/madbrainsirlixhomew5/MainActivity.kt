package com.example.madbrainsirlixhomew5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var menuButton: ImageView
    lateinit var toFragmentButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        if (actionBar!=null) actionBar.hide()
        menuButton = findViewById(R.id.imageViewMenuIcon)
        menuButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        toFragmentButton = findViewById(R.id.textViewReels)
        toFragmentButton.setOnClickListener {
            addFragment()
        }

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ContactsFragment.newInstance("chiki"))
            .addToBackStack(ContactsFragment.toString())
            .commit()
    }
}