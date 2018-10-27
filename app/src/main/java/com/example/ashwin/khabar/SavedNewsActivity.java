package com.example.ashwin.khabar;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ashwin.khabar.Adapters.NewsItemAdapter;
import com.example.ashwin.khabar.Adapters.SavedItemAdapter;
import com.example.ashwin.khabar.Helpers.HelperFunctions;
import com.example.ashwin.khabar.Models.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    String url = "";
    ArrayList<NewsItem> newsItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);

        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.saved_news_toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        //recycler view stuff
        recyclerView = findViewById(R.id.saved_news_rv_id);
        newsItemList = new ArrayList<>(NewsItem.listAll(NewsItem.class));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        SavedItemAdapter savedItemAdapter = new SavedItemAdapter(newsItemList,new SavedItemAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(NewsItem item)
            {
                HelperFunctions.loadPage(item.getSourceUrl(),getApplicationContext());
            }

        });
        recyclerView.setAdapter(savedItemAdapter);
        if(newsItemList.size() == 0)
        {
            TextView textView = findViewById(R.id.not_found_text_saved);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
