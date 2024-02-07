package testing;

//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import androidx.test.espresso.matcher.ViewMatchers;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.content.Intent;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class listingDetailsTest {

    private static final int SIMULATED_DELAY_MS = 500;


    @Rule
    public ActivityScenarioRule<ShowDetails> listingDetailsRule = new ActivityScenarioRule<>(ShowDetails.class);


    @Before
    public void setUp() {
        // Setup common test conditions or initialization here
    }

    @After
    public void tearDown() {
        // Tear down or clean up after each test
    }


    @Test
    public void testlistingDetails() {
        //Intent intent = new Intent();
        //intent.putExtra("listing_id", 10);

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        ActivityScenario<ShowDetails> scenario = listingDetailsRule.getScenario();
        //ActivityScenario<ShowDetails> scenario = ActivityScenario.launch(intent);


        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        Espresso.onView(ViewMatchers.withId(R.id.DetCost))
                .check(matches(withText("1200.0")));


    }

}


