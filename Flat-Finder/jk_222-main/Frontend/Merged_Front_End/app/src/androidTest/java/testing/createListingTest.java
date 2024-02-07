package testing;

//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import androidx.test.espresso.matcher.ViewMatchers;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.volleytrials.CreateListing;
import com.example.volleytrials.R;
import com.example.volleytrials.ShowDetails;
import com.example.volleytrials.UserData;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class createListingTest {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<CreateListing> createListingRule = new ActivityScenarioRule<>(CreateListing.class);


    @Before
    public void setUp() {
        // Setup common test conditions or initialization here
    }

    @After
    public void tearDown() {
        // Tear down or clean up after each test
    }


    @Test
    public void testCreateListing() {
        UserData.setID("21");

        String name = "Carter's Test Listing";
        String address = "1999 Newman DR";
        String price = "9000";
        String pets = "yes";
        String bedrooms = "15";
        String bathrooms = "10";
        String amenities = "Study Tables";

        ActivityScenario<CreateListing> scenario = createListingRule.getScenario();


        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        Espresso.onView(ViewMatchers.withId(R.id.et0)).perform(ViewActions.typeText(name), ViewActions.closeSoftKeyboard());
        //Type Password
        Espresso.onView(ViewMatchers.withId(R.id.et1)).perform(ViewActions.typeText(address), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.et2)).perform(ViewActions.typeText(price), ViewActions.closeSoftKeyboard());
        //Type Password
        Espresso.onView(ViewMatchers.withId(R.id.et3)).perform(ViewActions.typeText(pets), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.et4)).perform(ViewActions.typeText(bedrooms), ViewActions.closeSoftKeyboard());
        //Type Password
        Espresso.onView(ViewMatchers.withId(R.id.et5)).perform(ViewActions.typeText(bathrooms), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.et6)).perform(ViewActions.typeText(amenities), ViewActions.closeSoftKeyboard());

        //Click Button
        Espresso.onView(ViewMatchers.withId(R.id.postCL)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }


        //scenario.onActivity(new ActivityScenario.ActivityAction<CreateListing>() {
        //    @Override
        //    public void perform(CreateListing activity) {
        //        // Now you are in the new activity, and you can check for the view
        //        Espresso.onView(ViewMatchers.withId(R.id.textView3))
        //                .check(matches(withText("HOME PAGE")));
        //    }
        //});
    }



}


