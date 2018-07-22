package com.example.ashwin.khabar;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ashwin.khabar.Helpers.HelperFunctions;
import com.example.ashwin.khabar.Models.NewsItem;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
{
    static String query = "";
    ArrayList<NewsItem> newsItems;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        newsItems = new ArrayList<>();
        final RecyclerView recyclerView = findViewById(R.id.results_rv_id);
        final ProgressBar progressBar = findViewById(R.id.progressbar_id);
        final TextView textView = findViewById(R.id.not_found_text);
        final View view = findViewById(R.id.not_found_id);
        Toolbar toolbar = findViewById(R.id.results_toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        query = intent.getStringExtra("query");
        final String JSON_URL = "https://newsapi.org/v2/everything?q="+query+"&apiKey=ae072e92b280480c99dc61a7d1f1c9b0";
        HelperFunctions.loadDataActivity(textView,view,progressBar,JSON_URL,newsItems,recyclerView,getApplicationContext());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void loadPage(String url,Context context)
    {
        Intent intent = new Intent(context,WebActivity.class);
        intent.putExtra("url",url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
