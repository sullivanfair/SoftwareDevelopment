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
public class applyTest {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<ShowDetails> detailsRule = new ActivityScenarioRule<>(ShowDetails.class);


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
        Intent intent = new Intent();
        intent.putExtra("listing_id", 10);

        UserData.setName("Connor");
        UserData.setPassword("qwe");

        ActivityScenario<ShowDetails> scenario = detailsRule.getScenario();


        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        //Click Button
        Espresso.onView(ViewMatchers.withId(R.id.applyBtn)).perform(ViewActions.click());

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


