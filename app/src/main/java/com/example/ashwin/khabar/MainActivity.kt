package com.example.ashwin.khabar

import android.content.Intent
import android.os.AsyncTask
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.example.ashwin.khabar.Adapters.ViewPagerAdapter
import com.example.ashwin.khabar.Fragments.Business
import com.example.ashwin.khabar.Fragments.Headlines
import com.example.ashwin.khabar.Fragments.Health
import com.example.ashwin.khabar.Fragments.Sports
import com.example.ashwin.khabar.Fragments.Technology
import com.example.ashwin.khabar.Helpers.HelperFunctions


import java.io.IOException

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_id)
        setSupportActionBar(toolbar)

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        editText = findViewById(R.id.search_text_id)
        editText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val intent = Intent(applicationContext, SearchActivity::class.java)
                intent.putExtra("query", editText.text.toString())
                startActivity(intent)
                return@OnEditorActionListener true
            }
            if (editText.text.toString() === "") {
                Toast.makeText(applicationContext, "Please enter some text", Toast.LENGTH_SHORT).show()
            }
            false
        })


        viewPagerAdapter.addFragment(Headlines(), "Headlines")
        viewPagerAdapter.addFragment(Sports(), "Sports")
        viewPagerAdapter.addFragment(Health(), "Health")
        viewPagerAdapter.addFragment(Technology(), "Technology")
        viewPagerAdapter.addFragment(Business(), "Business")

        viewPager.adapter = viewPagerAdapter
        val tabLayout = findViewById<TabLayout>(R.id.tab_id)
        tabLayout.setupWithViewPager(viewPager)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_id -> {
                HelperFunctions.visibilityToggle(editText, this)
                return true
            }
            R.id.saved_items_init_id -> {
                val intent = Intent(applicationContext, SavedNewsActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
