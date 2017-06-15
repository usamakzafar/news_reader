package com.usamakzafar.newsreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.utils.HelpingMethods;

/**
 * Created by usamazafar on 15/06/2017.
 */

public class CommentsViewHolder extends RecyclerView.ViewHolder{
    private TextView text,author, time, replies, space;
    private Context context;

    public CommentsViewHolder(Context c, View itemView) {
        super(itemView);

        this.context = c;

        time     = (TextView) itemView.findViewById(R.id.tv_time);
        author   = (TextView) itemView.findViewById(R.id.tv_author);
        replies  = (TextView) itemView.findViewById(R.id.tv_replies);
        text     = (TextView) itemView.findViewById(R.id.tv_commentText);
        space    = (TextView) itemView.findViewById(R.id.space);
    }

    public void clearAnimation()
    {
        itemView.clearAnimation();
    }

    public TextView getText() {
        return text;
    }

    public TextView getAuthor() {
        return author;
    }

    public TextView getTime() {
        return time;
    }

    public TextView getReplies() {
        return replies;
    }

    public TextView getSpace() {
        return space;
    }

    public Context getContext() {
        return context;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void addCommentToView(Comment comment, int position){
        // Populate the view
        text.setText(comment.getText());
        text.setMaxLines(20);
        author.setText(comment.getAuthor());

        String howLongAgo = (String) DateUtils.getRelativeTimeSpanString(
                comment.getTime().getTimeInMillis(),
                System.currentTimeMillis(),
                1);
        time.setText(howLongAgo);

        String numOfReplies = comment.getKids() != null ? String.valueOf(comment.getKids().length()) : "0";
        replies.setText( numOfReplies + " replies");

        // Set the space on the left to denote a reply thread
        String spaceString = "";
        for(int i=0;i<comment.getLevel();i++) {
            spaceString += "     ";
        }
        space.setText(spaceString);

        setAnimation(this.itemView, position);

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


}