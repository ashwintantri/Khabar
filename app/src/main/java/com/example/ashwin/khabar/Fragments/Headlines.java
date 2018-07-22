package com.example.ashwin.khabar.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashwin.khabar.Models.NewsItem;
import com.example.ashwin.khabar.Helpers.HelperFunctions;
import com.example.ashwin.khabar.R;

import java.util.ArrayList;

public class Headlines extends Fragment
{

    private static final String JSON_URL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=key";
    ArrayList<NewsItem> newsItems;
    RecyclerView recyclerView;
    View rootView;

    public Headlines()
    {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        newsItems = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment,container,false);
        recyclerView = rootView.findViewById(R.id.top_news_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final SwipeRefreshLayout refreshLayout = rootView.findViewById(R.id.refresh_id);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                HelperFunctions.loadData(rootView,JSON_URL,newsItems,recyclerView,getActivity().getApplicationContext());
                refreshLayout.setRefreshing(false);
            }
        });
        HelperFunctions.loadData(rootView,JSON_URL,newsItems,recyclerView,getActivity().getApplicationContext());
        return rootView;
    }
}
