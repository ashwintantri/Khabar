package com.example.ashwin.khabar.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashwin.khabar.MainActivity;
import com.example.ashwin.khabar.Models.NewsItem;
import com.example.ashwin.khabar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SavedItemAdapter extends RecyclerView.Adapter<SavedItemAdapter.NewsItemViewHolder>
{

    public interface OnItemClickListener
    {
        void onItemClick(NewsItem item);
    }




    public static class NewsItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView headLinesText;
        Button deleteButton;

        public Context context;

        public NewsItemViewHolder(View view)
        {
            super(view);
            this.headLinesText = view.findViewById(R.id.saved_news_headlines_id);
            this.deleteButton = view.findViewById(R.id.delete_saved_news_id);
            context = view.getContext();
        }
        public void bind(final NewsItem newsItem,final OnItemClickListener listener)
        {
            headLinesText.setText(newsItem.getHeadlinesText());
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(newsItem);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    NewsItem.findById(NewsItem.class,newsItem.getId()).delete();
                    Snackbar.make(v,"Deleted",Snackbar.LENGTH_SHORT).show();
                    NavUtils.navigateUpFromSameTask((Activity)context);
                }
            });
        }

    }

    private ArrayList<NewsItem> newsItems;
    private final OnItemClickListener listener;

    public SavedItemAdapter(ArrayList<NewsItem> newsItems, OnItemClickListener listener)
    {
        this.newsItems = newsItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item,parent,false);
        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsItemViewHolder holder,final int position)
    {
        holder.bind(newsItems.get(position),listener);
    }

    @Override
    public int getItemCount()
    {
        return newsItems.size();
    }
}
