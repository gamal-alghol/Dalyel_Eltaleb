package com.dalyel.dalyelaltaleb;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.dalyel.dalyelaltaleb.View.DoctorActivity;

import org.junit.Rule;
import org.junit.Test;
import androidx.test.espresso.contrib.RecyclerViewActions;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class DoctorActivityRecycleTest {
    private static final int ITEM_BELOW_THE_FOLD = 3    ;
    @Rule
    public ActivityScenarioRule<DoctorActivity> activityScenarioRule =
            new ActivityScenarioRule<>(DoctorActivity.class);

    @Test(expected = PerformException.class)
    public void itemWithText_doesNotExist() {
        onView(ViewMatchers.withId(R.id.recyclerView_doctors))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("احمد الجدبة"))
                ));
    }


}
