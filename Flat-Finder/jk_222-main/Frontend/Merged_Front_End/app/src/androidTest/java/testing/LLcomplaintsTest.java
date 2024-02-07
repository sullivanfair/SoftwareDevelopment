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
import com.example.volleytrials.LLComplaints;
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
public class LLcomplaintsTest {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<LLComplaints> lLComplaintsRule = new ActivityScenarioRule<>(LLComplaints.class);


    @Before
    public void setUp() {
        // Setup common test conditions or initialization here
    }

    @After
    public void tearDown() {
        // Tear down or clean up after each test
    }


    @Test
    public void testViewComplaints() {
        UserData.setID("21");

        ActivityScenario<LLComplaints> scenario = lLComplaintsRule.getScenario();


        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }


        Espresso.onView(ViewMatchers.withId(R.id.llcTitle))
                .check(matches(withText("Your Complaints")));
    }



}


