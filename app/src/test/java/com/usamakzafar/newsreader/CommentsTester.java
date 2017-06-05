package com.usamakzafar.newsreader;

import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by usamazafar on 05/06/2017.
 */

public class CommentsTester {

    private Comment comment;

    public void testIfCommentIsParsingCorrectly(){

        String commentJson= "{\"by\":\"wallnuss\",\"id\":14467718,\"kids\":[14467781,14468256,14470479,14469443,14470448],\"parent\":14458955,\"text\":\"The only sad part of this story is the design was meant to celebrate John Conway and his Game of Life [1] (Conway actually was a lecturer at Cambridge, when he introduced GoL).<p>[1] From 2014: <a href=\\\"http:&#x2F;&#x2F;www.railwaygazette.com&#x2F;news&#x2F;infrastructure&#x2F;single-view&#x2F;view&#x2F;cambridge-science-park-station-to-incorporate-game-of-life.html\\\" rel=\\\"nofollow\\\">http:&#x2F;&#x2F;www.railwaygazette.com&#x2F;news&#x2F;infrastructure&#x2F;single-vie...</a>\",\"time\":1496381915,\"type\":\"comment\"}";

        comment = ParseJSON.parseComments(commentJson);
        assertNotNull(comment);

    }

    public void checkVariablesReturningCorrectly() {
        assertEquals("wallnuss", comment.getAuthor() );
        assertEquals("comment",  comment.getType()   );
        assertEquals(14467718,   comment.getId()     );
    }
}
