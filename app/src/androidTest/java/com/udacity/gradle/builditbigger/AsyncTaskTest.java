package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import timber.log.Timber;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Noah on 6/28/2017.
 */
@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest {
    private Context mContext;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mContext = getInstrumentation().getTargetContext();
    }
    @Test
    public void nonEmptyStringTest() {
        Timber.d("AsyncTaskTest");
        String joke = null;
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(mContext, null);
        endpointsAsyncTask.execute();
        try {
            joke = endpointsAsyncTask.get();
            Timber.d("Retrieved joke: " + joke);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(joke);
    }
}