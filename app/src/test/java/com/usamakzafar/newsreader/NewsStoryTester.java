package com.usamakzafar.newsreader;

import android.content.Context;
import android.test.mock.MockContext;

import com.usamakzafar.newsreader.adapters.CommentsAdapter;
import com.usamakzafar.newsreader.adapters.NewsStoryAdapter;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.models.NewsStory;
import com.usamakzafar.newsreader.network.NewsStoryNetworkCalls;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by usamazafar on 05/06/2017.
 */

public class NewsStoryTester {

    private NewsStory story;
    private NewsStoryAdapter adapter;

    @Mock
    Context mMockContext;


    public void testIfNewsStoryIsParsingCorrectly(){
        String news= "{\"by\":\"risk\",\"descendants\":13,\"id\":14474956,\"kids\":[14475330,14475607,14475244,14475259,14475303,14475553,14475334],\"score\":176,\"time\":1496462842,\"title\":\"SeaGlass – Enabling City-Wide IMSI-Catcher Detection\",\"type\":\"story\",\"url\":\"https://seaglass.cs.washington.edu/\"}";

        story = ParseJSON.parseNewsStory(news);
        assertNotNull(story);

    }

    public void checkVariablesReturningCorrectly() {
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(1496462842 * 1000L);

        assertEquals("SeaGlass – Enabling City-Wide IMSI-Catcher Detection", story.getTitle());
        assertEquals("risk",    story.getAuthor());
        assertEquals(14474956,  story.getId());
        assertEquals(176,       story.getScore());
        assertEquals(13,        story.getDescendants());
        assertEquals(7         ,story.getKids().length());
        assertEquals(time      ,story.getTime());
        assertEquals("story"   ,story.getType());
        assertEquals("https://seaglass.cs.washington.edu/" ,story.getUrl());
    }

    public void checkIfAdapterIsWorkingCorrectly() {

        List<NewsStory> newsStoryList = new ArrayList<>();
        newsStoryList.add(story);

        adapter = new NewsStoryAdapter(mMockContext,newsStoryList);

        assertNotNull(adapter);
        assertEquals(1, adapter.getItemCount());

    }


    public void checkfetcher() {


    }
}
