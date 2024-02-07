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
public class SystemTests {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<Login> loginRule = new ActivityScenarioRule<>(Login.class);

    //@Rule
    //public ActivityScenarioRule<Homepage> homepageRule = new ActivityScenarioRule<>(Homepage.class);



    @Before
    public void setUp() {
        // Setup common test conditions or initialization here
    }

    @After
    public void tearDown() {
        // Tear down or clean up after each test
    }


    @Test
    public void testLogin(){
        String userName = "Walter";
        String password = "White";

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        ActivityScenario<Login> scenario = loginRule.getScenario();

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        //Type name
        Espresso.onView(ViewMatchers.withId(R.id.editTextName)).perform(ViewActions.typeText(userName), ViewActions.closeSoftKeyboard());
        //Type Password
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword)).perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());
        //Click Button
        Espresso.onView(ViewMatchers.withId(R.id.buttonSubmit)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        //Test if activity changed as expected
        Espresso.onView(ViewMatchers.withId(R.id.textView3))
                .check(matches(withText("HOME PAGE")));


    }

}

