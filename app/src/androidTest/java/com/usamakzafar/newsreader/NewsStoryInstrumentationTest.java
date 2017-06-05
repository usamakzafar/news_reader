package com.usamakzafar.newsreader;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.usamakzafar.newsreader.network.NetworkHandler;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.verify;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class NewsStoryInstrumentationTest {

    private NewsStoryActivityTester tester;

    @Rule
    public ActivityTestRule<NewsActivity> rule  = new ActivityTestRule<>(NewsActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        NetworkHandler.isMocked = true;
        rule.launchActivity(new Intent());
        tester = new NewsStoryActivityTester(rule);
    }

    @Test
    public void test_a_if_recycler_view_exists(){
        tester.checkIfRecyclerViewExists();
    }

    @Test
    public void test_b_if_recycler_view_is_populated(){
        tester.checkIfRecyclerViewExists();
        tester.checkIfRecyclerViewIsPopulated();
    }
    @Test
    public void test_c_check_activity_transition(){
        tester.checkIfRecyclerViewExists();
        tester.checkIfRecyclerViewIsPopulated();
        tester.checkIfRowZeroExists();
        tester.clickRowZeroAndVerifyCommentsActivityStarted();
        tester.verifyIfCorrectValuesWerePassedToTheNewActivity();
    }



}