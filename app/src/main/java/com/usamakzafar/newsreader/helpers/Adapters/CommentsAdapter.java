package com.usamakzafar.newsreader.helpers.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.helpers.HelpingMethods;
import com.usamakzafar.newsreader.helpers.Objects.Comment;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private ArrayList<Comment> comments;
    private Context context;

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        public TextView text,score,author, time, replies, space;

        public CommentViewHolder(View itemView) {
            super(itemView);
            time     = (TextView) itemView.findViewById(R.id.tv_time);
            score    = (TextView) itemView.findViewById(R.id.tv_level);
            author   = (TextView) itemView.findViewById(R.id.tv_author);
            replies  = (TextView) itemView.findViewById(R.id.tv_replies);
            text     = (TextView) itemView.findViewById(R.id.tv_commentText);
            space    = (TextView) itemView.findViewById(R.id.space);
        }
        public void clearAnimation()
        {
            itemView.clearAnimation();
        }
    }

    public CommentsAdapter(Context _context, ArrayList<Comment> _comments){
        this.comments = _comments; this.context = _context;
    }

    @Override
    public CommentsAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_item, parent, false);

        return new CommentsAdapter.CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.CommentViewHolder holder, int position) {
        try {
            Comment comment = comments.get(position);

            //Set the necessary texts
            holder.text.setText(Html.fromHtml(Html.fromHtml(comment.getText()).toString()));
            holder.text.setMaxLines(20);
            holder.author.setText(comment.getAuthor());
            holder.score.setText(       String.valueOf(comment.getLevel()));
            holder.time.setText(HelpingMethods.parseDate(comment.getTime()));

            holder.replies.setText(   comment.getKids() != null ? String.valueOf(comment.getKids().length()) : "0");

            // Set the space on the left to denote a reply thread
            String space = "";
            for(int i=0;i<comment.getLevel();i++)
                space+= "   ";
            holder.space.setText(space);

            setAnimation(holder.itemView, position);
        }catch (Exception e) {e.printStackTrace();}
    }



    @Override
    public int getItemCount() {
        return comments.size();
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
    public void onViewDetachedFromWindow(CommentsAdapter.CommentViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

}

