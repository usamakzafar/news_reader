package com.usamakzafar.newsreader.network;

import android.content.Context;

import com.usamakzafar.newsreader.models.NewsStory;

/**
 * Created by usamazafar on 04/06/2017.
 */

public class NetworkHandler {

    public static boolean isMocked = false;

    public static NewsStoryNetworkCalls getNewsStory(Context c, NewsStoryNetworkCalls.NewsStoriesUpdatedListener listener){
        if (isMocked)
            return new MockNewsStoryNetworkCalls(c,listener);
        else
            return new NewsStoryNetworkCalls(c,listener);
    }


    public static CommentsNetworkCalls getComments(Context c, CommentsNetworkCalls.CommentsUpdatedListener listener){
        if (isMocked)
            return new MockCommentNetworkCalls(c,listener);
        else
            return new CommentsNetworkCalls(c,listener);
    }
}
