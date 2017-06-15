package com.usamakzafar.newsreader;

import android.content.Context;
import android.text.format.DateUtils;

import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by usamazafar on 03/06/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class HelpingMethodsTest {
    @Mock
    Context mMockContext;

    @Test
    public void test_a_method_compileURL() throws Exception{
        // Given a mocked Context injected into the object under test...
        when(mMockContext.getString(R.string.RestURL))
                .thenReturn("https://hacker-news.firebaseio.com/v0/item/");

        for (int i=0;i<10;i++) {
            int id = anyInt();
            assertEquals("https://hacker-news.firebaseio.com/v0/item/"+id+".json", HelpingMethods.compileURLforFetchingItems(mMockContext, id));
        }
    }

    @Test
    public void test_b_method_readNewsIDsFromString() throws Exception{

        String testString = "[14474956,14475236,14473377,14475489,14471931,14474663,14468818,14459536,14474426,14473886,14472517,14473077,14474222,14472265,14472166,14471153,14470415,14470231,14469645,14472177,14471358,14468355,14470496,14467890,14471668,14468471,14468602,14468376,14469477,14467520,14472086,14470967,14468848,14463260,14474157,14468362,14475156,14468663,14468613,14471049,14460013,14471706,14468844,14463509,14474837,14471725,14461084,14463385,14458648,14459150,14452832,14460777,14471238,14465895,14467702,14460810,14462785,14458955,14469317,14472353,14473936,14475166,14461799,14462384,14469316,14463732,14475110,14460121,14466917,14462842,14467869,14462633,14473243,14452923,14460517,14466740,14463911,14460085,14460963,14459417,14463066,14456871,14462533,14452819,14473579,14461640,14470377,14446261,14459409,14467181,14458971,14463874,14460429,14459245,14463219,14462266,14470098,14456242,14458203,14458548,14448404,14459004,14458230,14458293,14450945,14458388,14458608,14459537,14473513,14459543,14459332,14472524,14468212,14468903,14462125,14458254,14461742,14468751,14463083,14456653,14469680,14458970,14453764,14460032,14459218,14474669,14459278,14450905,14460713,14465566,14467330,14454477,14457866,14453962,14468554,14458368,14451651,14473300,14458233,14466833,14474501,14463617,14444914,14456495,14451294,14460016,14461097,14453622,14458906,14459343,14458124,14458071,14461704,14462799,14458552,14453301,14463993,14469779,14458789,14459526,14460083,14472181,14453942,14464690,14465243,14450448,14462217,14458242,14465534,14454446,14457245,14472466,14456649,14453263,14464415,14453273,14455611,14450158,14459933,14456675,14447727,14454194,14437244,14466721,14455346,14453406,14451601,14464864,14448911,14453588,14446602,14439692,14452004,14472918,14453456,14450879,14459382,14443533,14450666,14452510,14450962,14455185,14454796,14456767,14467196,14460574,14457080,14461880,14447393,14452011,14450618,14441492,14456459,14435219,14437754,14458832,14457019,14465546,14473394,14434053,14448763,14451822,14444507,14433725,14447964,14456690,14452482,14447682,14472957,14463989,14440507,14455202,14471911,14461843,14457710,14451873,14454236,14442579,14465667,14440993,14470462,14443398,14452731,14459002,14447885,14450162,14446350,14459182,14451718,14452583,14444030,14451015,14453512,14464033,14470193,14437689,14445239,14446759,14438601,14463423,14448056,14436417,14470280,14434043,14468096,14452904,14447286,14470979,14455247,14443589,14446204,14468529,14451628,14446516,14462927,14439789,14444819,14453344,14445597,14445412,14447961,14445257,14453992,14446004,14444202,14451627,14446018,14457067,14443002,14453583,14456562,14440849,14447329,14454498,14470316,14466691,14462628,14445728,14464907,14450924,14456913,14464681,14446373,14438550,14441407,14468980,14444793,14439863,14453096,14455172,14443789,14469133,14456911,14462132,14459115,14456842,14448674,14441673,14453937,14471466,14445775,14453281,14459985,14470314,14447603,14451148,14437007,14463704,14443402,14443146,14454189,14450432,14434209,14438487,14445048,14445802,14439137,14434254,14447598,14443968,14461447,14468162,14435106,14470277,14451509,14449725,14436704,14446248,14439636,14453966,14458103,14456023,14469981,14439615,14446679,14432994,14468082,14442715,14463009,14469526,14443191,14463982,14440466,14441364,14462783,14447294,14439820,14433163,14450150,14439517,14453545,14448044,14454916,14469189,14456524,14462959,14466210,14465582,14437092,14446608,14444443,14446401,14445598,14434086,14443040,14446186,14438657,14433015,14436381,14450327,14438703,14455069,14446708,14447330,14447336,14435987,14468739,14449806,14435270,14454289,14443291,14445015,14454196,14437511,14432857,14442100,14440805,14444440,14453548,14436239,14438462,14443915,14467321,14468525,14452781,14434080,14453530,14441063,14432912,14437679,14437921,14437117,14438472,14438612,14440053,14441068,14435471,14466999,14432754,14438051,14451623,14444465,14441552,14434233,14435130,14457787,14459669,14457246,14452041,14467777,14437404,14439098,14438377,14454593,14455219,14441022,14466033,14467681,14467646,14465842,14464074,14442354,14434019,14454530,14462735,14433772,14438207,14435838,14458105,14442382,14434547,14439178,14434845,14465222,14456353,14460536,14433618,14437632,14467257,14437923,14450234,14450675,14436550,14433805,14452328,14437450,14433687,14434457,14434821,14455290,14436494,14458682,14442410,14432368,14444179,14432833,14447678,14451664,14456049,14434988,14435436,14439471,14454178,14459876,14438567,14443759,14432809,14449580,14456784,14439555]";

        ArrayList<Integer> list = HelpingMethods.readNewsIDsFromString(testString);
        assertEquals(Integer.valueOf(14474956), list.get(0));
        assertEquals(Integer.valueOf(14475236), list.get(1));
        assertEquals(Integer.valueOf(14473377), list.get(2));
        assertEquals(Integer.valueOf(14475489), list.get(3));
        assertEquals(Integer.valueOf(14471931), list.get(4));

    }

    @Test
    public void test_c_method_parseDate() throws Exception{


    }

}
