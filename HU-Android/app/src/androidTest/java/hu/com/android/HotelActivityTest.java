package hu.com.android;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hu.com.android.activities.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by JGabrielFreitas on 17/03/16.
 */
@RunWith(AndroidJUnit4.class)
public class HotelActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void activateApplication() throws InterruptedException {
        Thread.sleep(2000); // wait response from server
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(R.id.edtSearch)).perform(typeText("Boulevard"));
        onData(anything()).inAdapterView(withId(R.id.hotelListView)).atPosition(0).perform(click());
        Thread.sleep(1500); // just to see the details
    }

}
