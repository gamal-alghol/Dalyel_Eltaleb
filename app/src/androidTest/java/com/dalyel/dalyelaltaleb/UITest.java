package com.dalyel.dalyelaltaleb;

import android.app.Activity;


import androidx.test.espresso.Espresso;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.dalyel.dalyelaltaleb.View.PublishActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;

import static androidx.test.espresso.matcher.ViewMatchers.withId;



@RunWith(AndroidJUnit4.class)
public class UITest extends Activity {

    @Rule
    public ActivityTestRule<PublishActivity> publishActivityActivityTestRule = new ActivityTestRule<>(PublishActivity.class);
    @Test
    public void testImgUserIsDisable() throws Throwable {
        publishActivityActivityTestRule.runOnUiThread(new Runnable() {
            public void run() {
                onView(withId(R.id.imageView6))
                        .perform(click())
                        .check(matches(isDisplayed()));
            }
        });

    }

    @Test
    public void testPublisBtnhIsClickable(){
        onView(withId(R.id.publish))
                .perform(click())
                .check(matches(isClickable()));

        onView(withId(R.id.publish)).perform(click());
        Espresso.closeSoftKeyboard();
    }

    @Test
    public  void testpublishEnaled(){
        onView(withId(R.id.publish))
                .perform(click())
                .check(matches(isEnabled()));

    }
    @Test
    public void countCLicked() {
        onView(withId(R.id.count)).perform(click());
        Espresso.pressBack();
    }





}
