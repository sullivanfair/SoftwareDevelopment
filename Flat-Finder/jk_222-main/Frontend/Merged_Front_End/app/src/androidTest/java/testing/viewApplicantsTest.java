package testing;

//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import androidx.test.espresso.matcher.ViewMatchers;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.CoreMatchers.allOf;

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
import com.example.volleytrials.ViewApplicants;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class viewApplicantsTest {

    private static final int SIMULATED_DELAY_MS = 1500;

    @Rule
    public ActivityScenarioRule<ViewApplicants> applicantsRule = new ActivityScenarioRule<>(ViewApplicants.class);


    @Before
    public void setUp() {
        // Setup common test conditions or initialization here
    }

    @After
    public void tearDown() {
        // Tear down or clean up after each test
    }


    @Test
    public void testViewApplicants() {

        UserData.setID("21");

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        ActivityScenario<ViewApplicants> scenario = applicantsRule.getScenario();


        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        Espresso.onView(allOf(withId(R.id.ApplicantName), isDescendantOfA(withId(R.id.applicantLayout1))))
                .check(matches(withText("Carter")));


    }

}


