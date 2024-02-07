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

public class EditProfileTest {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<EditProfile> editRule = new ActivityScenarioRule<>(EditProfile.class);

    @Test
    public void testUpdateButtonClicked() {
        ActivityScenario<EditProfile> scenario = editRule.getScenario();

        String testUser = "TestNewTenant";
        String testPassword = "TestNewPassword";
        String testPrice = "2000";
        String testLocation = "TestNewLocation";
        String testPets = "No";
        String testRoom = "7";
        String testBath = "3";
        String testEmail = "newTest@example.com";

        Espresso.onView(withId(R.id.editName)).perform(ViewActions.typeText(testUser));
        Espresso.onView(withId(R.id.editPassword)).perform(ViewActions.typeText(testPassword));
        Espresso.onView(withId(R.id.editEmail)).perform(ViewActions.typeText(testEmail));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editPrice)).perform(ViewActions.typeText(testPrice));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editLocation)).perform(ViewActions.typeText(testLocation));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editPets)).perform(ViewActions.typeText(testPets));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editRoom)).perform(ViewActions.typeText(testRoom));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editBath)).perform(ViewActions.typeText(testBath));
    }

    @Test
    public void testBackButtonClicked(){
        ActivityScenario<EditProfile> scenario = editRule.getScenario();
        Espresso.onView(withId(R.id.buttonEditLogIn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Name")));
    }

}
