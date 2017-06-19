package com.usamakzafar.newsreader.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.usamakzafar.newsreader.CommentsActivity;
import com.usamakzafar.newsreader.NewsActivity;
import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.models.NewsStory;
import com.usamakzafar.newsreader.utils.HelpingMethods;

/**
 * Created by usamazafar on 20/06/2017.
 */

public class NewsStoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView title,score,author, time,comments;
    private Context context;
    private NewsStory story;

    public NewsStoryViewHolder(Context c, View itemView) {
        super(itemView);

        this.context = c;

        itemView.setOnClickListener(this);

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

    public void setNewsStoryToView(NewsStory _story, int position) {
        this.story = _story;

        title.setText(story.getTitle());
        author.setText(story.getAuthor());
        score.setText(       String.valueOf(story.getScore()));
        comments.setText(    String.valueOf(story.getDescendants()));

        String howLongAgo = (String) DateUtils.getRelativeTimeSpanString(
                story.getTime().getTimeInMillis(),
                System.currentTimeMillis(),
                1);
        time.setText(howLongAgo);

        setAnimation(itemView, position);

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
    public void onClick(View v) {
        //Check if the News Story has any replies
        if (story.getKids().length() > 0 && story.getDescendants() >0) {

            //Make Intent for Comments Activity
            Intent intent = new Intent(context, CommentsActivity.class);
            intent.putExtra("title", story.getTitle());
            intent.putExtra("kids", story.getKids().toString());

            //Start Intent with a little animation
            context.startActivity(intent);
            ((NewsActivity) context).overridePendingTransition(R.anim.left_from_right, R.anim.right_from_left);
        }
        else{
            HelpingMethods.showMessage(context,context.getString(R.string.no_comments_message));
        }
    }
}