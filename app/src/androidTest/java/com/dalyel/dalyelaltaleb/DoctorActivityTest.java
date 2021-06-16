package com.dalyel.dalyelaltaleb;

import androidx.test.espresso.DataInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.dalyel.dalyelaltaleb.View.DoctorActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

public class DoctorActivityTest {

    // Match the last item by matching its text.
    private static final String LAST_ITEM_ID = "يوسف عاشور";
    @Rule
    public ActivityScenarioRule<DoctorActivity> rule = new ActivityScenarioRule<>(
            DoctorActivity.class);

    @Test
    public void lastItem_NotDisplayed() {
        // Last item should not exist if the list wasn't scrolled down.
        onView(withText(LAST_ITEM_ID)).check(doesNotExist());

    }




    private static DataInteraction onRow(String str) {
        return onData(hasEntry(equalTo(DoctorActivity.ROW_TEXT), is(str)));
    }

}