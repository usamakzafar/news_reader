package com.usamakzafar.newsreader;

import android.content.Context;

import com.usamakzafar.newsreader.network.CommentsNetworkCalls;
import com.usamakzafar.newsreader.network.NetworkHandler;
import com.usamakzafar.newsreader.network.NewsStoryNetworkCalls;
import com.usamakzafar.newsreader.utils.HelpingMethods;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

/**
 * Created by usamazafar on 19/06/2017.
 */

public class NetworkMethodsTest {

    @Test
    public void NetworkCallGetterTest() throws Exception{
       // NetworkHandler handler = mock(NetworkHandler.class);
        NetworkHandler handler = new NetworkHandler();
        assertNotNull(handler);
/*
        NewsStoryNetworkCalls newsStoryNetworkCalls = handler.getNewsStory(any(Context.class),any(NewsStoryNetworkCalls.NewsStoriesUpdatedListener.class));
        assertNotNull(newsStoryNetworkCalls);

        CommentsNetworkCalls commentsNetworkCalls = handler.getComments(any(Context.class),any(CommentsNetworkCalls.CommentsUpdatedListener.class));
        assertNotNull(commentsNetworkCalls);*/

    }

    @Test
    public void HTTPCallTest() throws Exception{

        HelpingMethods call = new HelpingMethods();
        HelpingMethods.mocked = true;
        //HTTPCallMethod call = mock(HelpingMethods.class);
        assertNotNull(call);
/*

        String s = call.makeHTTPCallForObject(anyString());
        assertNotNull(s);
        assertEquals("Mocked return", s);

        s = call.makeHTTPCallForObject(anyString());
        assertNotNull(s);
        assertEquals("Mocked return",s);
*/

    }
}
