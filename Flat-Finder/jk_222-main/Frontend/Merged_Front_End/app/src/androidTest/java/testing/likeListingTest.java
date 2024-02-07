package testing;

//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import androidx.test.espresso.matcher.ViewMatchers;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.CoreMatchers.allOf;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;

import com.example.volleytrials.Homepage;
import com.example.volleytrials.LandHomepage;
import com.example.volleytrials.Login;
import com.example.volleytrials.R;
import com.example.volleytrials.ShowDetails;
import com.example.volleytrials.TopListings;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class likeListingTest {

    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule
    public ActivityScenarioRule<TopListings> listingsRule = new ActivityScenarioRule<>(TopListings.class);


    @Before
    public void setUp() {
        // Setup common test conditions or initialization here
    }

    @After
    public void tearDown() {
        // Tear down or clean up after each test
    }


    @Test
    public void testLike(){

        String beforeLike = "7";
        String afterLike = "8";


        ActivityScenario<TopListings> scenario = listingsRule.getScenario();

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        Espresso.onView(allOf(withId(R.id.NumLikes), isDescendantOfA(ViewMatchers.withId(R.id.listingLayout1))))
                .check(matches(withText(beforeLike)));


        Espresso.onView(allOf(withId(R.id.Like), isDescendantOfA(ViewMatchers.withId(R.id.listingLayout1))))
                .perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        Espresso.onView(allOf(withId(R.id.NumLikes), isDescendantOfA(ViewMatchers.withId(R.id.listingLayout1))))
                .check(matches(withText(afterLike)));


        Espresso.onView(allOf(withId(R.id.Like), isDescendantOfA(ViewMatchers.withId(R.id.listingLayout1))))
                .perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        Espresso.onView(allOf(withId(R.id.NumLikes), isDescendantOfA(ViewMatchers.withId(R.id.listingLayout1))))
                .check(matches(withText(beforeLike)));
    }

}

