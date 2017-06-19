package com.usamakzafar.newsreader.robolectric.newsactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.usamakzafar.newsreader.CommentsActivity;
import com.usamakzafar.newsreader.NewsActivity;
import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.adapters.CommentsViewHolder;
import com.usamakzafar.newsreader.adapters.NewsStoryViewHolder;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.models.NewsStory;
import com.usamakzafar.newsreader.network.NetworkHandler;
import com.usamakzafar.newsreader.network.NewsStoryNetworkCalls;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by usamazafar on 18/06/2017.
 */

public class NewsActivityUnitTester {


    private NewsActivity activity;
    private CommentsActivity commentsActivity;
    private NewsStoryNetworkCalls.NewsStoriesUpdatedListener  listener;
    private List<NewsStory> mockNewsStories = new ArrayList<>();;
    private RecyclerView recyclerView;

    public NewsActivityUnitTester(NewsActivity activity) {
        this.activity = activity;
    }


    // Setup Methods
    public void setUpMockNetworkCall() {
        HelpingMethods mockCall = mock(HelpingMethods.class);

        HelpingMethods.mocked = true;
        try {
            when(mockCall.makeHTTPCallForArray(anyString())).thenReturn("[14584042,14583882,14583566,14585129,14584377,14583530,14584637,14585523,14583480,14583402,14581273,14584868,14582470,14582846,14583017,14584997,14583273,14579530,14580680,14585041,14584164,14579080,14578380,14581228,14583668,14584606,14581219,14579076,14582278,14581639,14580238,14580603,14580095,14578807,14580464,14584092,14580115,14580337,14577014,14581034,14579188,14582481,14583690,14576934,14579263,14579949,14579729,14582766,14583647,14583476,14579491,14582471,14579465,14582222,14574444,14584527,14581919,14580481,14580482,14582884,14582101,14582187,14579728,14571701,14568468,14572255,14578227,14582674,14582307,14582342,14581871,14568146,14582033,14581837,14580556,14570002,14580352,14580746,14574283,14575501,14573900,14579612,14580163,14563939,14578269,14584015,14578509,14571242,14580362,14568558,14577348,14579726,14575056,14580521,14574831,14577195,14576077,14584453,14574014,14577873,14575361,14581682,14573695,14576912,14563621,14568610,14575970,14580698,14577881,14572244,14565611,14581074,14577145,14573643,14578077,14581245,14564084,14579909,14567559,14558106,14576693,14582391,14578542,14566118,14573257,14571038,14570035,14558582,14561134,14577828,14579214,14576677,14573996,14573308,14575058,14575153,14577816,14572482,14579248,14577604,14569905,14569365,14575695,14574906,14575885,14564624,14575777,14570574,14562463,14572742,14579362,14577268,14564962,14583503,14577100,14577859,14568418,14576005,14558811,14571641,14575465,14561498,14575756,14578578,14566691,14572298,14577682,14574122,14571785,14563219,14572585,14558031,14573959,14557910,14558035,14557362,14581859,14568185,14572583,14568180,14562136,14560106,14558893,14580724,14581596,14575416,14572625,14570220,14567539,14567074,14574560,14561369,14567807,14579499,14570684,14558775,14575895,14572971,14557572,14573814,14573345,14583775,14562399,14563720,14571453,14570807,14559223,14573837,14573774,14564248,14569217,14576376,14566481,14563516,14556901,14568614,14568063,14583148,14576291,14563870,14579635,14576772,14559177,14560128,14569777,14557929,14568583,14576963,14573002,14565501,14581696,14561936,14574547,14578697,14559087,14574289,14571761,14570369,14564945,14576571,14562432,14562326,14565466,14559632,14566173,14578271,14570590,14573236,14566608,14579014,14568813,14574163,14557546,14566771,14557977,14557172,14565675,14579194,14560042,14575128,14557532,14567492,14561791,14557230,14564455,14562722,14567554,14564048,14572542,14560076,14563780,14576931,14564622,14565316,14564279,14565418,14574357,14575717,14564489,14557178,14578553,14560995,14569876,14576782,14568133,14565132,14573951,14564189,14564182,14569008,14579885,14562777,14579769,14564007,14580728,14579008,14579572,14567488,14567114,14577570,14568172,14575886,14577336,14574205,14568646,14577484,14570315,14570156,14559317,14569266,14578540,14565062,14573685,14562889,14570044,14562314,14566059,14562214,14581244,14566583,14558103,14570003,14561799,14560054,14558466,14565322,14571426,14574902,14557135,14560501,14573815,14565198,14573739,14557601,14571390,14558585,14574596,14572577,14565614,14564322,14574628,14577146,14562007,14563271,14561637,14558894,14559054,14568050,14557456,14565361,14557776,14563648,14574553,14567760,14570079,14567823,14569100,14564364,14560471,14558037,14571393,14571050,14557487,14575032,14575000,14573135,14573131,14573115,14574932,14574861,14572847,14561712,14577901,14564112,14564360,14560067,14567687,14559370,14559240,14562040,14566179,14564964,14564857,14567624,14575627,14558768,14573297,14573104,14567683,14565212,14565177,14570163,14563199,14564059,14563610,14571803,14557909,14574512,14570698,14566264,14557200,14566053,14557829,14569476,14565711,14557575,14571725,14568673,14568600,14559256,14562128,14568367,14564200,14569154,14563985,14567903,14567750,14565451,14557833,14557632,14567411,14565595,14565251,14565171,14569213,14564271,14563903,14562545,14561990,14571360,14568460,14560538,14565713,14563118,14557296,14583312,14575014,14568693,14559428,14559882,14559812,14574917,14582418,14558819,14570997,14577045,14560640,14577124,14567803,14583651]");
            when(mockCall.makeHTTPCallForObject(anyString())).thenReturn("{\"by\":\"andrewke\",\"descendants\":33,\"id\":14584042,\"kids\":[14584672,14584821,14584353,14584391,14584423,14584705,14585073,14584458,14584979,14584813,14584655,14584426,14584792,14584680,14584875,14585400],\"score\":219,\"time\":1497835856,\"title\":\"NSA OSS Technologies\",\"type\":\"story\",\"url\":\"https://nationalsecurityagency.github.io\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        activity.getStoryNetworkCalls().setHttpCall(mockCall);
        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
    }

    public void checkInitActivity() {
        activity.initActivity();
        assertNotNull(activity);
        assertNotNull(activity.getHandler());
        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        assertNotNull(recyclerView);
        assertNotNull(recyclerView.getAdapter());
    }

    public void checkIfNewsStoriesAreLoaded() {
        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        assertNotNull(recyclerView);
        assertNotNull(recyclerView.getAdapter());
        assertTrue(recyclerView.getAdapter().getItemCount() > 0);

    }

    public void checkIfMoreNewsStoriesAreLoading() {
        int prevCount = NewsActivity.getCurrentCount();
        activity.fetchMoreNewsStories();
        assertTrue(NewsActivity.getCurrentCount()>prevCount);
        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        assertTrue(recyclerView.getAdapter().getItemCount() > 20);
    }

    public void checkIfRecyclerViewExists() {
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        assertNotNull(recyclerView);
    }

    public void checkIfRecyclerViewIsPopulated(){
        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        assertTrue(recyclerView.getAdapter().getItemCount() > 0);
    }

    public void checkTitleBar() {
        ActionBar actionBar =  activity.getSupportActionBar();
        assertNotNull(actionBar);
        assertNotNull(actionBar.getTitle());
        assertEquals("News Reader",actionBar.getTitle().toString());
    }

    public void checkSwipeRefreshLayout() {
        SwipeRefreshLayout swipeRefreshLayout =  (SwipeRefreshLayout) activity.findViewById(R.id.swipe_refresh_layout);
        assertNotNull(swipeRefreshLayout);
    }

    public void checkIfClickingStartsNewActivity() {

        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 10000);
        recyclerView.getChildAt(0).performClick();

        Intent expectedIntent = new Intent(activity, CommentsActivity.class);
        expectedIntent.putExtra("title","NSA OSS Technologies");
        expectedIntent.putExtra("kids","[14584672,14584821,14584353,14584391,14584423,14584705,14585073,14584458,14584979,14584813,14584655,14584426,14584792,14584680,14584875,14585400]");
      //  assertThat(shadowOf(activity).getNextStartedActivity()).isEqualTo(expectedIntent);
    }


    public void testNewsStoryViewHolder() throws JSONException {

        View itemView = LayoutInflater.from(activity)
                .inflate(R.layout.news_list_item_fancy, null, false);
        NewsStoryViewHolder holder = new NewsStoryViewHolder(activity, itemView);

        NewsStory newsStory = getDummyNewsStory();

        holder.setNewsStoryToView(newsStory);

        Assert.assertNotNull(holder);

        Assert.assertNotNull(holder.getAuthor());
        Assert.assertEquals("andrewke",holder.getAuthor().getText().toString());

        Assert.assertNotNull(holder.getScore());
        Assert.assertEquals("219",holder.getScore().getText().toString());

        Assert.assertNotNull(holder.getTitle());
        Assert.assertEquals("NSA OSS Technologies",holder.getTitle().getText().toString());

        Assert.assertNotNull(holder.getComments());
        Assert.assertEquals("33",holder.getComments().getText().toString());

    }



    public NewsStory getDummyNewsStory() throws JSONException {
        return ParseJSON.parseNewsStory("{\"by\":\"andrewke\",\"descendants\":33,\"id\":14584042,\"kids\":[14584672,14584821,14584353,14584391,14584423,14584705,14585073,14584458,14584979,14584813,14584655,14584426,14584792,14584680,14584875,14585400],\"score\":219,\"time\":1497835856,\"title\":\"NSA OSS Technologies\",\"type\":\"story\",\"url\":\"https://nationalsecurityagency.github.io\"}");
    }
}
