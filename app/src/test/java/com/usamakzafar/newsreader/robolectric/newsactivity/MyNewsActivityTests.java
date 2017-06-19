package com.usamakzafar.newsreader.robolectric.newsactivity;

import com.usamakzafar.newsreader.BuildConfig;
import com.usamakzafar.newsreader.NewsActivity;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by usamazafar on 18/06/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MyNewsActivityTests {

    private NewsActivityUnitTester tester;

    @Before
    public void setup() throws Exception {
        final ActivityController<NewsActivity> activity = Robolectric.buildActivity(NewsActivity.class);

        tester = new NewsActivityUnitTester(activity.get());
        tester.setUpMockNetworkCall();
        activity.create().start().resume();
    }

    @Test
    public void checkIfNewsStoriesAreLoaded(){
        tester.checkInitActivity();
        tester.checkIfNewsStoriesAreLoaded();
    }

    @Test
    public void checkIfMoreNewsStoriesAreLoading(){
        tester.checkInitActivity();
        tester.checkIfNewsStoriesAreLoaded();
        tester.checkIfMoreNewsStoriesAreLoading();
    }

    @Test
    public void checkIfViewsLoadedCorrectly(){
        tester.checkTitleBar();
        tester.checkSwipeRefreshLayout();
    }

    @Test
    public void checkIfRecyclerViewIsLoading(){
        tester.checkInitActivity();
        tester.checkIfRecyclerViewExists();
        tester.checkIfRecyclerViewIsPopulated();
        tester.checkIfNewsStoriesAreLoaded();
        tester.checkIfMoreNewsStoriesAreLoading();

    }


    @Test
    public void checkClickingStartsNewActivity() {
        tester.checkInitActivity();
        tester.checkIfRecyclerViewExists();
        tester.checkIfRecyclerViewIsPopulated();
        tester.checkIfNewsStoriesAreLoaded();
        tester.checkIfMoreNewsStoriesAreLoading();
        tester.checkIfClickingStartsNewActivity();
    }


    @Test
    public void testNewsStoryViewHolder() throws JSONException {
        tester.testNewsStoryViewHolder();
    }
}
