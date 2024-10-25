package androidsamples.java.journalapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;

import android.view.View;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

/**
 * Instrumented tests for the {@link EntryDetailsFragment}.
 */
@RunWith(AndroidJUnit4.class)
public class EntryDetailsFragmentTest {

  @Rule
  public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


  @BeforeClass
  public static void enableAccessibilityChecks() {
    AccessibilityChecks.enable();
  }




  @Test
  public void testDeletion() {
    onView(withId(R.id.btn_add_entry)).perform(click());
    onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Hello"));
    onView(withId(R.id.btn_save)).perform(click());
    onView(anyOf(withText("Hello"))).perform(click());
    onView(withId(R.id.delete)).perform(click());
    onView(withText("OK")).perform(click());
    onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion(0));
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
//  public void testh() {
//    // Click on "Add" button to create a new entry
//    onView(withId(R.id.btn_add_entry)).perform(click());
//
//    // Enter text and save the entry
//    onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing"));
//    onView(withId(R.id.btn_save)).perform(click());
//
//    // Check if the entry is displayed in the RecyclerView
//    onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion(1));
//    onView(anyOf(withText("Testing"))).perform(click());
//
//    // Rotate the device to simulate a screen rotation
//    onView(isRoot()).perform(orientationLandscape());
//
//    // Check if the entry is still displayed after rotation
//    onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withText("Testing"))));
//
//    // Delete the entry
//    onView(withId(R.id.delete)).perform(click());
//    onView(withText("OK")).perform(click());
//  }

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
    onView(withId(R.id.infoTitle)).check(matches(withText("Journal App")));
  }

  @Test
  public void testToolbarPresence() {
    onView(withId(R.id.btn_add_entry)).perform(click());
    onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing"));
    onView(withId(R.id.btn_save)).perform(click());
    onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    onView(anyOf(withText("Testing"))).perform(click());
    onView(withId(R.id.delete)).perform(click());
    onView(withText("OK")).perform(click());
  }


  @Test
  public void testInsertion() {
    onView(withId(R.id.btn_add_entry)).perform(click());
    onView(withId(R.id.edit_title)).perform(clearText()).perform(typeText("Testing"));
    onView(withId(R.id.btn_save)).perform(click());
    onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion(1));
    onView(anyOf(withText("Testing"))).perform(click());
    onView(withId(R.id.delete)).perform(click());
    onView(withText("OK")).perform(click());
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