package com.usamakzafar.newsreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.models.Comment;

import java.util.List;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {

    private List<Comment> comments;
    private Context context;
    

    public CommentsAdapter(Context _context, List<Comment> _comments){
        this.comments = _comments; this.context = _context;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_item, parent, false);

        return new CommentsViewHolder(this.context,itemView);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        try {
            Comment comment = comments.get(position);
            holder.addCommentToView(comment,position);

        }catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public void onViewDetachedFromWindow(CommentsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

}

