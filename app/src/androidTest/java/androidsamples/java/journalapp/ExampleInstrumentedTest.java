package androidsamples.java.journalapp;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;

import static androidsamples.java.journalapp.CustomViewActions.setText;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.accessibility.AccessibilityChecks;

import java.util.Objects;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
    }

    @Test
    public void testAddEntryButtonAccessibility() {
        onView(withId(R.id.btn_add_entry)).perform(click());
    }
    @Test
    public void testDeletion() {
        onView(withId(R.id.btn_add_entry)).perform(click());
        onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Hello"));
        onView(withId(R.id.btn_entry_date)).perform(setText("Sat, Oct 26, 2024"));
        onView(withId(R.id.btn_start_time)).perform(setText("10:00 AM"));
        onView(withId(R.id.btn_end_time)).perform(setText("02:00 PM"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(anyOf(withText("Hello"))).perform(click());
        onView(withId(R.id.delete)).perform(click());
        onView(withText("OK")).perform(click());
    }

    @Test
    public void testNavigationToEntryListFragment() {
        // Create a TestNavHostController
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());

        FragmentScenario<EntryListFragment> entryDetailsFragmentFragmentScenario
                = FragmentScenario.launchInContainer(EntryListFragment.class, null, R.style.Theme_JournalApp, (FragmentFactory) null);

        entryDetailsFragmentFragmentScenario.onFragment(fragment -> {
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph);

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController);
        });
        onView(withId(R.id.btn_add_entry)).perform(click());
        assertThat(Objects.requireNonNull(navController.getCurrentDestination()).getId(), is(R.id.entryDetailsFragment));

    }



//  @Test
//  public void testNavigationToDetailFragment() {
//    // Click on an item in the RecyclerView
//    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
//    // Check if the detail fragment is displayed
//    onView(withId(R.id.layout_entry_detail)).check(matches(isDisplayed()));
//  }


    @Test
    public void testEntryDetailDisplay() {
        onView(withId(R.id.btn_add_entry)).perform(click());
        onView(withId(R.id.record_entry)).check(matches(withText("Record Entry")));
    }

    @Test
    public void infoDetailDisplay() {
        onView(withId(R.id.info)).perform(click());
    }

    @Test
    public void ShareButtonDisplay() {
        onView(withId(R.id.btn_add_entry)).perform(click());
        onView(withId(R.id.share)).perform(click());
    }

    @Test
    public void testToolbarPresence() {
        onView(withId(R.id.btn_add_entry)).perform(click());
        onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(anyOf(withText("Testing"))).perform(click());
        onView(withId(R.id.delete)).perform(click());
        onView(withText("OK")).perform(click());
    }





    @Test
    public void testInsertion() {
        // Click the add entry button
        onView(withId(R.id.btn_add_entry)).perform(click());

        // Set the title
        onView(withId(R.id.edit_title)).perform(clearText(), typeText("Testing"));

        // Open date picker and set date

        onView(withId(R.id.btn_entry_date)).perform(setText("Sat, Oct 26, 2024"));
        onView(withId(R.id.btn_start_time)).perform(setText("10:00 AM"));
        onView(withId(R.id.btn_end_time)).perform(setText("02:00 PM"));

        // Click OK on time picker dialog

        // Save the journal entry
        onView(withId(R.id.btn_save)).perform(click());


        // Set start and end times similarly if you have a TimePicker


        // Verify the entry count and interact with the entry
        onView(anyOf(withText("Testing"))).perform(click());
    }




    public static class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assert adapter != null;
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

}