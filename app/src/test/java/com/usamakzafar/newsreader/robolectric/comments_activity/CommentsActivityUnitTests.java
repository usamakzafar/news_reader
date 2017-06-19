package com.usamakzafar.newsreader.robolectric.comments_activity;

import android.content.Intent;

import com.usamakzafar.newsreader.BuildConfig;
import com.usamakzafar.newsreader.CommentsActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static junit.framework.Assert.assertEquals;

/**
 * Created by usamazafar on 20/06/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CommentsActivityUnitTests {
    private CommentsActivityTester tester;

    @Before
    public void setup() throws Exception {
        Intent intent = new Intent(ShadowApplication.getInstance().getApplicationContext(),
                CommentsActivity.class);
        intent.putExtra("title", "Title 1");
        intent.putExtra("kids", "[1,2,3,4]");

        final ActivityController<CommentsActivity> activity = Robolectric.buildActivity(CommentsActivity.class).withIntent(intent);

        tester = new CommentsActivityTester(activity.get());
        tester.setUpMockNetworkCall();


        activity.create().start().resume();
    }

    @Test
    public void test_ifIntentWasSuccessfullyParsed(){
        tester.checkIntentNotNull();
        tester.checkTitlePassedInIntent();
        tester.checkChildrenPassedInIntent();
    }

    @Test
    public void test_ActionBarTitle() {
        tester.checkIntentNotNull();
        tester.checkTitlePassedInIntent();
        tester.checkActionBarNotNull();
        tester.checkActionBarTitle();
    }
}
