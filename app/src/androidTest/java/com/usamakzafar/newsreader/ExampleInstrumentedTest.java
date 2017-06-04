package com.usamakzafar.newsreader;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.usamakzafar.newsreader.models.NewsStory;
import com.usamakzafar.newsreader.network.NewsStoryNetworkCalls;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleInstrumentedTest extends ActivityInstrumentationTestCase2<NewsActivity> {

    @Mock
    private NewsStoryNetworkCalls calls;

    public ExampleInstrumentedTest(Class<NewsActivity> activityClass) {
        super(activityClass);
    }

    @Override
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {

                getActivity().onProgressUpdated(new ArrayList<NewsStory>());

                return null;

            }
        }).when(calls).execute(anyInt(),anyList());
        super.setUp();
    }

    @Test
    public void test1(){


        NewsActivity activity = getActivity();

        RecyclerView viewById =  (RecyclerView) activity.findViewById(R.id.recyclerview);
        assertNotNull(viewById);
        assertEquals(viewById.getChildCount(), 0);
    }

    /*
    @Rule
    public ActivityTestRule<NewsActivity> rule  = new ActivityTestRule<>(NewsActivity.class);

    @Before
    public void setUp(){

    }

    @Test
    public void useAppContext() throws Exception {

        NewsStoryNetworkCalls call = mock(NewsStoryNetworkCalls.class);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {

                rule.getActivity().onProgressUpdated(new ArrayList<NewsStory>());

                return null;

            }
        }).when(call).execute(anyInt(),anyList());

        NewsActivity activity = rule.getActivity();

        RecyclerView viewById =  (RecyclerView) activity.findViewById(R.id.recyclerview);
        assertNotNull(viewById);
        assertEquals(viewById.getChildCount(), 0);
    }*/
}
