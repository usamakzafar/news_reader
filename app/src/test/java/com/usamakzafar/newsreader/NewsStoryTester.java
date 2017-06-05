package com.usamakzafar.newsreader;

import com.usamakzafar.newsreader.models.NewsStory;
import com.usamakzafar.newsreader.utils.ParseJSON;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by usamazafar on 05/06/2017.
 */

public class NewsStoryTester {

    private NewsStory story;

    public void testIfNewsStoryIsParsingCorrectly(){
        String news= "{\"by\":\"risk\",\"descendants\":13,\"id\":14474956,\"kids\":[14475330,14475607,14475244,14475259,14475303,14475553,14475334],\"score\":176,\"time\":1496462842,\"title\":\"SeaGlass – Enabling City-Wide IMSI-Catcher Detection\",\"type\":\"story\",\"url\":\"https://seaglass.cs.washington.edu/\"}";

        story = ParseJSON.parseNewsStory(news);
        assertNotNull(story);

    }

    public void checkVariablesReturningCorrectly() {
        assertEquals("SeaGlass – Enabling City-Wide IMSI-Catcher Detection", story.getTitle());
        assertEquals("risk",    story.getAuthor());
        assertEquals(14474956,  story.getId());
        assertEquals(176,       story.getScore());
        assertEquals(13,        story.getDescendants());
    }
}
