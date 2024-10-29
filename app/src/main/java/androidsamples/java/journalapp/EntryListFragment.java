package androidsamples.java.journalapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {
  private JournalViewModel mJournalViewModel;
  private static final String TAG = "EntryListFragment";
  private View view;
  private boolean isNavigating = false;
  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    Log.d(TAG, "onAttach called");
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate called");
    super.onCreate(savedInstanceState);
    mJournalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView called");
    view = inflater.inflate(R.layout.fragment_entry_list, container, false);

    FloatingActionButton addEntryButton = view.findViewById(R.id.btn_add_entry);
    addEntryButton.setContentDescription("Add new journal entry"); // TalkBack description
    addEntryButton.setOnClickListener(v -> {
      JournalEntry entry = new JournalEntry("", "Start Time", "End Time", "Date");
      mJournalViewModel.insert(entry);
    });

    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    JournalEntryListAdapter adapter = new JournalEntryListAdapter(view.getContext());
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    recyclerView.setContentDescription("List of journal entries"); // TalkBack description

    mJournalViewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> {
      adapter.setEntries(entries);
    });

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Log.d(TAG, "onViewCreated called");

    mJournalViewModel.getLastInsertedEntryId().observe(getViewLifecycleOwner(), entryId -> {
      if (entryId != null && !isNavigating) {
        isNavigating = true;
        EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
        action.setEntryId(entryId);

        try {
          Navigation.findNavController(view).navigate(action);
        } catch (IllegalArgumentException e) {
          Log.e(TAG, "Navigation action already handled.");
        }

        mJournalViewModel.clearLastInsertedEntryId();
      }
    });
  }
;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.d(TAG, "onActivityCreated called");
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.d(TAG, "onStart called");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume called");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.d(TAG, "onPause called");
    isNavigating = false;
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.d(TAG, "onStop called");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    Log.d(TAG, "onDestroyView called");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy called");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    Log.d(TAG, "onDetach called");
  }





  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_entry_list, menu);

  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.info) {
      openAuthorWebpage();
      return true; // Return true to indicate that the click was handled
    }
    return super.onOptionsItemSelected(item);
  }

  private void openAuthorWebpage() {
    String url = "https://jamesclear.com/atomic-habits";
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(url));
    startActivity(intent);
  }



}