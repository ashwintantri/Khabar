package com.example.ashwin.khabar.Fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.ashwin.khabar.Models.NewsItem
import com.example.ashwin.khabar.Helpers.HelperFunctions
import com.example.ashwin.khabar.R

import org.json.JSONObject

import java.util.ArrayList


class Technology : Fragment() {
    lateinit var newsItems: ArrayList<NewsItem>
    lateinit var recyclerView: RecyclerView
    lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsItems = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment, container, false)
        recyclerView = rootView.findViewById(R.id.top_news_id)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val refreshLayout = rootView.findViewById<SwipeRefreshLayout>(R.id.refresh_id)
        refreshLayout.setOnRefreshListener {
            HelperFunctions.loadData(rootView, JSON_URL, newsItems, recyclerView, activity!!.applicationContext)
            refreshLayout.isRefreshing = false
        }
        HelperFunctions.loadData(rootView, JSON_URL, newsItems, recyclerView, activity!!.applicationContext)
        return rootView
    }
        private val JSON_URL = "https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=ae072e92b280480c99dc61a7d1f1c9b0"
}
