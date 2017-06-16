package com.usamakzafar.newsreader;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.usamakzafar.newsreader.adapters.CommentsAdapter;
import com.usamakzafar.newsreader.adapters.CommentsViewHolder;
import com.usamakzafar.newsreader.listener.RecyclerItemClickListener;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.network.NetworkHandler;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

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

    @Test
    public void test_c_verify_correct_data_present_in_recycler_view(){
        tester.checkIfRecyclerViewExists();
        tester.checkIfRecyclerViewIsPopulated();
        tester.checkifTheFirstChildIsPresent();
        tester.checkTheDataInTheFirstChild();
    }

    @Test
    public void test_d_check_if_actionbar_is_present(){
        tester.checkIfActionBarExists();
    }

    @Test
    public void test_e_check_if_actionbar_shows_correct_title(){
        tester.checkIfActionBarExists();
        tester.checkIfActionbarShowsCorrectTitle();
    }

    @Test
    public void test_f_adapterViewHolder(){

        String commentJson= "{\"by\":\"wallnuss\",\"id\":14467718" +
                ",\"kids\":[14467781,14468256,14470479,14469443,14470448]" +
                ",\"parent\":14458955,\"text\":\"sample text\"" +
                ",\"time\":1496381915,\"type\":\"comment\"}";

        Comment comment = ParseJSON.parseComments(commentJson);
        comment.setLevel(0);
        Assert.assertNotNull(comment);

        LayoutInflater jomarzi = LayoutInflater.from(mActivityRule.getActivity());
        View view = jomarzi.inflate(R.layout.comment_list_item,null);

        assertNotNull(view);

        CommentsViewHolder holder = new CommentsViewHolder(mActivityRule.getActivity(),view);
        assertNotNull(holder);

        //CommentsViewHolder spyholder = spy(holder);
        //doNothing().when(spyholder);
        holder.addCommentToView(comment,0);


        assertEquals(holder.getAuthor().getText()         ,comment.getAuthor()         );
        assertEquals(holder.getReplies().getText()        ,comment.getKids().length() + " replies"  );
        assertEquals(holder.getText().getText()           ,comment.getText()           );

        String howLongAgo = (String) DateUtils.getRelativeTimeSpanString(
                comment.getTime().getTimeInMillis(),
                System.currentTimeMillis(),
                1);

        assertEquals(holder.getTime().getText()           ,howLongAgo);

    }

    @Test
    public void test_g_listener(){
        //tester.listener();
        tester.viewholder();
    }

}
