package com.usamakzafar.newsreader;


import android.text.Html;
import android.text.Spanned;

import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.models.NewsStory;
import com.usamakzafar.newsreader.network.NetworkHandler;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by usamazafar on 3/06/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class NewsStoryUnitTesting {

    private NewsStoryTester tester;

    @Before
    public void setUp(){
        tester= new NewsStoryTester();
        NetworkHandler.isMocked = true;
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_a_parsing() throws Exception{
        tester.testIfNewsStoryIsParsingCorrectly();
    }

    @Test
    public void test_b_variables() throws Exception{
        tester.testIfNewsStoryIsParsingCorrectly();
        tester.checkVariablesReturningCorrectly();
    }

    @Test
    public void test_c_checkAdapter() throws Exception{
        tester.testIfNewsStoryIsParsingCorrectly();
        tester.checkVariablesReturningCorrectly();
        tester.checkIfAdapterIsWorkingCorrectly();
    }

    @Test
    public void test_d_checkFetcher() throws Exception{
        tester.checkfetcher();
    }

}
