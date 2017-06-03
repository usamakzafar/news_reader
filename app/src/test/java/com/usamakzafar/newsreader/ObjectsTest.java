package com.usamakzafar.newsreader;


import com.usamakzafar.newsreader.helpers.ParseJSON;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.*;

/**
 * Created by usamazafar on 3/06/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ObjectsTest {

    @Test
    public void testNewsStoryParsing() throws Exception{

        String news= "{\"by\":\"risk\",\"descendants\":13,\"id\":14474956,\"kids\":[14475330,14475607,14475244,14475259,14475303,14475553,14475334],\"score\":176,\"time\":1496462842,\"title\":\"SeaGlass – Enabling City-Wide IMSI-Catcher Detection\",\"type\":\"story\",\"url\":\"https://seaglass.cs.washington.edu/\"}";

        assertEquals("SeaGlass – Enabling City-Wide IMSI-Catcher Detection", ParseJSON.parseNewsStory(news).getTitle());
        assertEquals("risk",    ParseJSON.parseNewsStory(news).getAuthor());
        assertEquals(14474956,  ParseJSON.parseNewsStory(news).getId());
        assertEquals(176,       ParseJSON.parseNewsStory(news).getScore());
        assertEquals(13,        ParseJSON.parseNewsStory(news).getDescendants());

    }

    @Test
    public void testCommentParsing() throws Exception{

        String comment= "{\"by\":\"wallnuss\",\"id\":14467718,\"kids\":[14467781,14468256,14470479,14469443,14470448],\"parent\":14458955,\"text\":\"The only sad part of this story is the design was meant to celebrate John Conway and his Game of Life [1] (Conway actually was a lecturer at Cambridge, when he introduced GoL).<p>[1] From 2014: <a href=\\\"http:&#x2F;&#x2F;www.railwaygazette.com&#x2F;news&#x2F;infrastructure&#x2F;single-view&#x2F;view&#x2F;cambridge-science-park-station-to-incorporate-game-of-life.html\\\" rel=\\\"nofollow\\\">http:&#x2F;&#x2F;www.railwaygazette.com&#x2F;news&#x2F;infrastructure&#x2F;single-vie...</a>\",\"time\":1496381915,\"type\":\"comment\"}";


/*
        //  create mock
        Html test = mock(Html.class);
        Iterator<String> i= mock(Iterator.class);

        // Given a mocked Context injected into the object under test...
        when(Html.fromHtml(i.next()).toString())
                .thenReturn("Sample Title");

        assertEquals("wallnuss", ParseJSON.parseComments(comment).getAuthor() );
        assertEquals("comment",  ParseJSON.parseComments(comment).getType()   );
        assertEquals(14467718,   ParseJSON.parseComments(comment).getId()     );
*/
    }

}
