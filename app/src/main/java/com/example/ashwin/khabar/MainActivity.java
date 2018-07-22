package com.example.ashwin.khabar;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashwin.khabar.Adapters.ViewPagerAdapter;
import com.example.ashwin.khabar.Fragments.Business;
import com.example.ashwin.khabar.Fragments.Headlines;
import com.example.ashwin.khabar.Fragments.Health;
import com.example.ashwin.khabar.Fragments.Sports;
import com.example.ashwin.khabar.Fragments.Technology;
import com.example.ashwin.khabar.Helpers.HelperFunctions;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{
    EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);


        final ViewPager viewPager = findViewById(R.id.pager);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        editText = findViewById(R.id.search_text_id);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v,int actionId,KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                    intent.putExtra("query",String.valueOf(editText.getText()));
                    startActivity(intent);
                    return true;
                }
                if(editText.getText().toString()=="")
                {
                    Toast.makeText(getApplicationContext(),"Please enter some text",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        viewPagerAdapter.addFragment(new Headlines(),"Headlines");
        viewPagerAdapter.addFragment(new Sports(),"Sports");
        viewPagerAdapter.addFragment(new Health(),"Health");
        viewPagerAdapter.addFragment(new Technology(),"Technology");
        viewPagerAdapter.addFragment(new Business(),"Business");

        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tab_id);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.search_id: HelperFunctions.visibilityToggle(editText,this); return true;
            case R.id.saved_items_init_id:Intent intent = new Intent(getApplicationContext(),SavedNewsActivity.class);
                                            startActivity(intent);
                                            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
