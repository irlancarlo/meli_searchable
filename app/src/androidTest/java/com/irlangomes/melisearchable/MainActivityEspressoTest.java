package com.irlangomes.melisearchable;

import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.irlangomes.melisearchable.activity.MainActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void findDataTest() {
//        onView(withId(com.miguelcatalan.materialsearchview.R.id.searchTextView));
//        onView(withId(com.miguelcatalan.materialsearchview.R.id.searchTextView)).perform(typeText("something"));
        MaterialSearchView searchView = activityRule.getActivity().findViewById(R.id.searchView);

        for(int i = 0; i < searchView.getChildCount(); i++) {
            if(searchView.getChildAt(i) instanceof EditText){
                onView(withId(searchView.getChildAt(i).getId())).perform(typeText("moto"+ '\n'));
            }
        }

    }
}
