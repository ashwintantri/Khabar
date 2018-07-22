package com.example.ashwin.khabar.Helpers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ashwin.khabar.Models.NewsItem;
import com.example.ashwin.khabar.Adapters.NewsItemAdapter;
import com.example.ashwin.khabar.R;
import com.example.ashwin.khabar.WebActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.view.View.INVISIBLE;

public class HelperFunctions
{

    public static void visibilityToggle(final EditText editText,final Context context)
    {
        if(editText.getVisibility()==View.VISIBLE)
        {
            editText.setVisibility(View.GONE);
        }
        else if(editText.getVisibility()== INVISIBLE || editText.getVisibility()==View.GONE)
        {
            editText.setVisibility(View.VISIBLE);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    editText.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager)  context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                }
            });
            editText.requestFocus();
        }
    }

    public static void loadData(final View rootView,String JSON_URL,final ArrayList<NewsItem> newsItems,final RecyclerView recyclerView,final Context context)
    {
        final ProgressBar progressBar = rootView.findViewById(R.id.progressbar_id);
        progressBar.setVisibility(rootView.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,JSON_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressBar.setVisibility(INVISIBLE);

                        try
                        {
                            JSONObject obj = new JSONObject(response);


                            JSONArray newsArray = obj.getJSONArray("articles");

                            for(int i = 0;i < newsArray.length();i++)
                            {
                                JSONObject newsObject = newsArray.getJSONObject(i);
                                String sourceUrl = newsObject.getString("url");
                                String details = newsObject.getString("description");
                                if(details.equals("null")||details.equals(""))
                                {
                                    details = "Click to see more.";
                                }
                                NewsItem news = new NewsItem(newsObject.getString("urlToImage"),newsObject.getString("title"),details,sourceUrl);
                                newsItems.add(news);
                            }
                            recyclerView.setAdapter(new NewsItemAdapter(newsItems,new NewsItemAdapter.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(NewsItem item)
                                {
                                    loadPage(item.getSourceUrl(),context);
                                }
                            }));
                        }catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void loadDataActivity(final TextView textView,final View view,final ProgressBar progressBar,String JSON_URL,final ArrayList<NewsItem> newsItems,final RecyclerView recyclerView,final Context context)
    {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,JSON_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressBar.setVisibility(View.INVISIBLE);

                        try
                        {
                            JSONObject obj = new JSONObject(response);


                            JSONArray newsArray = obj.getJSONArray("articles");

                            for(int i = 0;i < newsArray.length();i++)
                            {
                                JSONObject newsObject = newsArray.getJSONObject(i);
                                String sourceUrl = newsObject.getString("url");
                                String details = newsObject.getString("description");
                                if(details.equals("null")||details.equals(""))
                                {
                                    details = "Click to see more.";
                                }
                                NewsItem news = new NewsItem(newsObject.getString("urlToImage"),newsObject.getString("title"),details,sourceUrl);
                                newsItems.add(news);
                            }
                            if(newsItems.size()==0)
                            {
                                textView.setVisibility(View.VISIBLE);
                                view.setVisibility(View.VISIBLE);
                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(new NewsItemAdapter(newsItems,new NewsItemAdapter.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(NewsItem item)
                                {
                                    loadPage(item.getSourceUrl(),context);
                                }
                            }));
                        }catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void loadPage(String url,Context context)
    {
        Intent intent = new Intent(context,WebActivity.class);
        intent.putExtra("url",url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
    }
    }
