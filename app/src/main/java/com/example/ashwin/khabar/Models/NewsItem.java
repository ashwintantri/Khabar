package com.example.ashwin.khabar.Models;

import com.orm.SugarRecord;

public class NewsItem extends SugarRecord<NewsItem>
{
    String photoUrl;
    String headlinesText;
    String newsDetailsText;
    String sourceUrl;

    public NewsItem()
    {}

    public NewsItem(String photoUrl,String headlinesText,String newsDetailsText,String sourceUrl)
    {
        this.photoUrl = photoUrl;
        this.headlinesText = headlinesText;
        this.newsDetailsText = newsDetailsText;
        this.sourceUrl = sourceUrl;
    }

    public String getHeadlinesText()
    {
        return headlinesText;
    }

    public String getNewsDetailsText()
    {
        return newsDetailsText;
    }

    public String getPhotoUrl()
    {
        return photoUrl;
    }

    public String getSourceUrl()
    {
        return sourceUrl;
    }


}
