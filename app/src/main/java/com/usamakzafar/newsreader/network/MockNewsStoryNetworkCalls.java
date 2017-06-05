package com.usamakzafar.newsreader.network;

import android.content.Context;

import com.usamakzafar.newsreader.models.NewsStory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by usamazafar on 04/06/2017.
 */

class MockNewsStoryNetworkCalls extends NewsStoryNetworkCalls {
    public MockNewsStoryNetworkCalls(Context c, NewsStoriesUpdatedListener listener) {
        super(c,listener);
    }

    @Override
    public void execute( int currentCount, List<NewsStory> newsStories){
        for(int i=1;i<=10;i++){

            //Dummy news stories for testing
            NewsStory n = new NewsStory();

            n.setAuthor("author " + i);
            n.setTitle("Title " + i);
            n.setId(i);
            n.setDescendants(5);
            n.setScore(i);
            n.setTime(Calendar.getInstance());
            n.setType("story");
            try {
                n.setKids(new JSONArray("[ 14482423, 14482678, 14482612, 14482519, 14482510, 14482576, 14482307, 14482483, 14482594, 14482614 ]"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            n.setUrl("");

            newsStories.add(n);
        }
        getListener().onProgressUpdated(newsStories);
    }
}
