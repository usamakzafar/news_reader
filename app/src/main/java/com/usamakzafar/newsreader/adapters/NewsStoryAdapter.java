package com.usamakzafar.newsreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.models.NewsStory;
import com.usamakzafar.newsreader.utils.HelpingMethods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class NewsStoryAdapter extends RecyclerView.Adapter<NewsStoryViewHolder> {

    private List<NewsStory> newsStories;
    private Context context;


    public NewsStoryAdapter(Context _context, List<NewsStory> _newsStories){
        this.newsStories = _newsStories; this.context = _context;
    }

    @Override
    public NewsStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item_fancy, parent, false);

        return new NewsStoryViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(NewsStoryViewHolder holder, int position) {
        try {
            NewsStory story = newsStories.get(position);

            holder.setNewsStoryToView(story);

            holder.setAnimation(position);
        }catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return newsStories.size();
    }

    @Override
    public void onViewDetachedFromWindow(NewsStoryViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ((NewsStoryViewHolder)holder).clearAnimation();
    }

}
