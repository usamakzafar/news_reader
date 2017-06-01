package com.usamakzafar.newsreader.helpers.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.helpers.NewsStory;

import java.util.ArrayList;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class NewsStoryAdapter extends RecyclerView.Adapter<NewsStoryAdapter.NewsStoryViewHolder> {

    private ArrayList<NewsStory> newsStories;

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
    }

    public NewsStoryAdapter(ArrayList<NewsStory> _newsStories){
        this.newsStories = _newsStories;
    }

    @Override
    public NewsStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);

        return new NewsStoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsStoryViewHolder holder, int position) {
        NewsStory story = newsStories.get(position);
        holder.title.setText(story.getTitle());
        holder.time.setText(story.getTime().getTime().toString());
        holder.score.setText(story.getScore());
        holder.author.setText(story.getAuthor());
        holder.comments.setText(story.getDescendants());
    }

    @Override
    public int getItemCount() {
        return newsStories.size();
    }

}
