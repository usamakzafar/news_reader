package com.usamakzafar.newsreader.network;

import android.content.Context;

import com.usamakzafar.newsreader.models.NewsStory;

/**
 * Created by usamazafar on 04/06/2017.
 */

public class NetworkHandler {

    public NewsStoryNetworkCalls getNewsStory(Context c, NewsStoryNetworkCalls.NewsStoriesUpdatedListener listener){
            return new NewsStoryNetworkCalls(c,listener);
    }


    public CommentsNetworkCalls getComments(Context c, CommentsNetworkCalls.CommentsUpdatedListener listener){
       // if (isMocked)
       //     return new MockCommentNetworkCalls(c,listener);
       // else
            return new CommentsNetworkCalls(c,listener);
    }
}
