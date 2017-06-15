package com.usamakzafar.newsreader;

import android.test.mock.MockContext;
import android.view.ViewGroup;

import com.usamakzafar.newsreader.adapters.CommentsAdapter;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;

/**
 * Created by usamazafar on 05/06/2017.
 */

public class CommentsTester {

    private Comment comment;
    private CommentsAdapter adapter;
    private MockContext context = new MockContext();

    public void testIfCommentIsParsingCorrectly(){

        String commentJson= "{\"by\":\"wallnuss\",\"id\":14467718" +
                ",\"kids\":[14467781,14468256,14470479,14469443,14470448]" +
                ",\"parent\":14458955,\"text\":\"sample text\"" +
                ",\"time\":1496381915,\"type\":\"comment\"}";

        comment = ParseJSON.parseComments(commentJson);
        comment.setLevel(0);
        assertNotNull(comment);

    }

    public void checkVariablesReturningCorrectly() {

        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(1496381915*1000L);

        assertEquals("wallnuss"    ,comment.getAuthor() );
        assertEquals("comment"     ,comment.getType()   );
        assertEquals(14467718      ,comment.getId()     );
        assertEquals(time          ,comment.getTime());
        assertEquals(5             ,comment.getKids().length());
        assertEquals(14458955      ,comment.getParentID());
        assertEquals("sample text" ,comment.getText());
        assertEquals(0             ,comment.getLevel());

    }

    public void checkIfAdapterIsWorkingCorrectly() {
        List<Comment> commentsList = new ArrayList<>();
        commentsList.add(comment);

        adapter = new CommentsAdapter(context,commentsList);

        assertNotNull(adapter);
        assertEquals(1, adapter.getItemCount());


    }

    public void checkViewHolder() {
    }
}
