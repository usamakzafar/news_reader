package com.usamakzafar.newsreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class NewsStoryAdapter extends RecyclerView.Adapter<NewsStoryAdapter.NewsStoryViewHolder> {

    private List<NewsStory> newsStories;
    private Context context;

    public class NewsStoryViewHolder extends RecyclerView.ViewHolder{
        public TextView title,score,author, time,comments;

        public NewsStoryViewHolder(View itemView) {
            super(itemView);
            time     = (TextView) itemView.findViewById(R.id.tv_time);
            score    = (TextView) itemView.findViewById(R.id.tv_score);
            author   = (TextView) itemView.findViewById(R.id.tv_author);
            comments = (TextView) itemView.findViewById(R.id.tv_comments);
            title    = (TextView) itemView.findViewById(R.id.tv_storyTitle);
        }

        public void clearAnimation()
        {
            itemView.clearAnimation();
        }
    }

    public NewsStoryAdapter(Context _context, List<NewsStory> _newsStories){
        this.newsStories = _newsStories; this.context = _context;
    }

    @Override
    public NewsStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item_fancy, parent, false);

        return new NewsStoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsStoryViewHolder holder, int position) {
        try {
            NewsStory story = newsStories.get(position);
            holder.title.setText(story.getTitle());
            holder.author.setText(story.getAuthor());
            holder.score.setText(       String.valueOf(story.getScore()));
            holder.comments.setText(    String.valueOf(story.getDescendants()));
            holder.time.setText(HelpingMethods.parseDate(story.getTime()));

            setAnimation(holder.itemView, position);
        }catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return newsStories.size();
    }

    //Animations . Just for fun! :)
    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position)   {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(NewsStoryViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ((NewsStoryViewHolder)holder).clearAnimation();
    }

}
