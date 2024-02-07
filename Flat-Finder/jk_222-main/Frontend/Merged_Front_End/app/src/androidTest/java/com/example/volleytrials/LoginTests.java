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

public class LoginTests {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<Login> loginRule = new ActivityScenarioRule<>(Login.class);

    @Test
    public void SignUpButtonTest() {
        ActivityScenario<Login> scenario = loginRule.getScenario();
        Espresso.onView(withId(R.id.buttonSignUp)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.textViewName)).check(matches(withText("Name")));
    }

    @Test
    public void testLoginButtonClicked() {
        ActivityScenario<Login> scenario = loginRule.getScenario();
        String testUserName = "TestTenant";
        String testPassword = "TestPassword";

        Espresso.onView(withId(R.id.editTextName)).perform(ViewActions.typeText(testUserName));
        Espresso.onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(testPassword));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.buttonSubmit)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.title)).check(matches(withText("HOME PAGE")));
    }
}
