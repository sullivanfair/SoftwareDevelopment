package com.example.volleytrials;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class HomepageTests {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<Homepage> homeRule = new ActivityScenarioRule<>(Homepage.class);

    @Before
    public void setUp(){
        UserData.getInstance().setArray(new JSONArray());
        Log.d("Array", UserData.getInstance().getArray().toString());
    }
    /*
    @Test
    public void ToEditButtonTest() {
        ActivityScenario<Homepage> scenario = homeRule.getScenario();
        Espresso.onView(withId(R.id.buttonToEdit)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.textViewName)).check(matches(withText("Name")));
    }

    @Test
    public void LogoutButtonTest() {
        ActivityScenario<Homepage> scenario = homeRule.getScenario();
        Espresso.onView(withId(R.id.buttonLogOut)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Name")));
    }

    @Test
    public void ToListingsButtonTest() {
        ActivityScenario<Homepage> scenario = homeRule.getScenario();
        Espresso.onView(withId(R.id.toListings)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.textView)).check(matches(withText("Your Listings")));
    }

    @Test
    public void NotifButtonTest() {
        ActivityScenario<Homepage> scenario = homeRule.getScenario();
        Espresso.onView(withId(R.id.toNotifications)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.textView)).check(matches(withText("Notifications")));
    }

    @Test
    public void ToSearchButtonTest() {
        ActivityScenario<Homepage> scenario = homeRule.getScenario();
        Espresso.onView(withId(R.id.toSearch)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.textView)).check(matches(withText("SEARCH")));
    }

    @Test
    public void ToGroupsButtonTest() {
        ActivityScenario<Homepage> scenario = homeRule.getScenario();
        Espresso.onView(withId(R.id.toGroups)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("GROUPS")));
    }

    @Test
    public void ToComplaintsButtonTest() {
        ActivityScenario<Homepage> scenario = homeRule.getScenario();
        Espresso.onView(withId(R.id.toComplaints)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.backTC)).check(matches(withText("Back")));
    }

     */
}
