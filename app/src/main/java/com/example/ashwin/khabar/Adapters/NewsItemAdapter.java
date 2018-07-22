package com.example.ashwin.khabar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashwin.khabar.Models.NewsItem;
import com.example.ashwin.khabar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsItemViewHolder>
{

    public interface OnItemClickListener
    {
        void onItemClick(NewsItem item);
    }



    public static class NewsItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView newsPhoto;
        TextView headLinesText;
        TextView newsDetailsText;
        Button shareButton;
        Button saveButton;

        public Context context;

        public NewsItemViewHolder(View view)
        {
            super(view);
            this.newsPhoto = view.findViewById(R.id.image_id);
            this.headLinesText = view.findViewById(R.id.headlines_id);
            this.newsDetailsText = view.findViewById(R.id.news_details_id);
            this.shareButton = view.findViewById(R.id.share_id);
            this.saveButton = view.findViewById(R.id.bookmark_id);
            context = view.getContext();
        }
        public void bind(final NewsItem newsItem,final OnItemClickListener listener)
        {
            if(newsItem.getPhotoUrl()!="null"||newsItem.getPhotoUrl()!="")
            {
                Picasso.get().load(newsItem.getPhotoUrl()).centerCrop().fit().into(newsPhoto);
            }
            else
            {
                Picasso.get().load("C:\\Users\\Ashwin\\Desktop\\projec\\Android\\Khabar\\app\\src\\main\\res\\drawable\\no_result.png").centerCrop().fit().into(newsPhoto);
            }
            headLinesText.setText(newsItem.getHeadlinesText());
            newsDetailsText.setText(newsItem.getNewsDetailsText());
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(newsItem);
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,newsItem.getSourceUrl());
                    context.startActivity(Intent.createChooser(intent,"Share"));
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    newsItem.save();
                    Toast.makeText(context,"Saved",Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private ArrayList<NewsItem> newsItems;
    private final OnItemClickListener listener;

    public NewsItemAdapter(ArrayList<NewsItem> newsItems, OnItemClickListener listener)
    {
        this.newsItems = newsItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder,int position)
    {
        holder.bind(newsItems.get(position),listener);

    }

    @Override
    public int getItemCount()
    {
        return newsItems.size();
    }
}
