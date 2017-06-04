package com.usamakzafar.newsreader.utils.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.models.Comment;

import java.util.ArrayList;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private ArrayList<Comment> comments;
    private Context context;

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        public TextView text,author, time, replies, space;

        public CommentViewHolder(View itemView) {
            super(itemView);
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

            // Populate the view
            holder.text.setText(comment.getText());
            holder.text.setMaxLines(20);
            holder.author.setText(comment.getAuthor());
            holder.time.setText(HelpingMethods.parseDate(comment.getTime()));

            String numOfReplies = comment.getKids() != null ? String.valueOf(comment.getKids().length()) : "0";
            holder.replies.setText( numOfReplies + " replies");

            // Set the space on the left to denote a reply thread
            String space = "";
            for(int i=0;i<comment.getLevel();i++)
                space+= "     ";
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
