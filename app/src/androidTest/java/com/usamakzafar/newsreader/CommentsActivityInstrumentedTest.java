package com.usamakzafar.newsreader;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.usamakzafar.newsreader.network.NetworkHandler;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by usamazafar on 05/06/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class CommentsActivityInstrumentedTest {

    private CommentsActivityTester tester;

    @Rule
    public ActivityTestRule<CommentsActivity> mActivityRule =
            new ActivityTestRule<CommentsActivity>(CommentsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, CommentsActivity.class);
                    result.putExtra("kids","[ 14482423, 14482678, 14482612, 14482519, 14482510, 14482576, 14482307, 14482483, 14482594, 14482614 ]");
                    result.putExtra("title", "Test Scenario");
                    return result;
                }

                @Override
                protected void beforeActivityLaunched() {
                    NetworkHandler.isMocked = true;
                    super.beforeActivityLaunched();
                }
            };

    @Before
    public void setUp() throws Exception {
        tester = new CommentsActivityTester(mActivityRule);
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


}
/*
        tester.test_if_recycler_view_is_populated();
        tester.test_if_recycler_view_is_not_null();


        1. check if actionbar not null
        2. verify if recycler view exists
        3. verify if recycler is populated with dummy data
        4. verify if recycler view's first child exhibits correct data
        5. verify activity title shown
        6. verify is clicking a row transitions to comments activity


         */
