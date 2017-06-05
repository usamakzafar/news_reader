package com.usamakzafar.newsreader;

import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.utils.HelpingMethods;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by usamazafar on 05/06/2017.
 */

public class CommentsUnitTesting {

    private CommentsTester tester;

    @Before
    public void setUp(){
        tester= new CommentsTester();
        HelpingMethods.mocked = true;
    }

    @Test
    public void test_a_parsing() throws Exception{
        tester.testIfCommentIsParsingCorrectly();

    }

    @Test
    public void test_b_variables() throws Exception{
        tester.testIfCommentIsParsingCorrectly();
        tester.checkVariablesReturningCorrectly();
    }
}
