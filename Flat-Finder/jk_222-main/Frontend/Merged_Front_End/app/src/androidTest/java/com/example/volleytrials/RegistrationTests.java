package com.example.volleytrials;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegistrationTests {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<Registration> regRule = new ActivityScenarioRule<>(Registration.class);

    @Test
    public void testRegistrationButtonClicked() {
        ActivityScenario<Registration> scenario = regRule.getScenario();

        String testUserTenant = "TestTenant";
        String testUserLandlord = "TestLandlord";
        String testPassword = "TestPassword";
        String testPrice = "1000";
        String testLocation = "TestLocation";
        String testPets = "Yes";
        String testRoom = "2";
        String testBath = "1";
        String testEmail = "test@example.com";

        Espresso.onView(withId(R.id.editTextName)).perform(ViewActions.typeText(testUserTenant));
        Espresso.onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(testPassword));
        Espresso.onView(withId(R.id.editTextEmail)).perform(ViewActions.typeText(testEmail));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.buttonRenter)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.editTextPrice)).perform(ViewActions.typeText(testPrice));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextLocation)).perform(ViewActions.typeText(testLocation));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPets)).perform(ViewActions.typeText(testPets));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextRoom)).perform(ViewActions.typeText(testRoom));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextBathroom)).perform(ViewActions.typeText(testBath));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.buttonCreate)).perform(ViewActions.click());

        Espresso.onView(withId(R.id.editTextName)).perform(ViewActions.typeText(testUserLandlord));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(testPassword));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextEmail)).perform(ViewActions.typeText(testEmail));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.buttonRenter)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.buttonCreate)).perform(ViewActions.click());
    }

    @Test
    public void testToLoginButtonClicked(){
        ActivityScenario<Registration> scenario = regRule.getScenario();
        Espresso.onView(withId(R.id.buttonLogIn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Name")));
    }

}
