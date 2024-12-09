package androidsamples.java.journalapp;

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
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidsamples.java.journalapp.database.JournalEntry;
import androidsamples.java.journalapp.database.JournalEntryListAdapter;
import androidsamples.java.journalapp.database.JournalViewModel;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {
  private FloatingActionButton btnAddEntry;
  private JournalViewModel mJournalViewModel;
  private static final String TAG = "EntryListFragment";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

   @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
     return inflater.inflate(R.layout.fragment_entry_list, container, false);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
    inflater.inflate(R.menu.entry_list_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);

      RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
      JournalEntryListAdapter adapter = new JournalEntryListAdapter(requireContext(), entry -> {
        Log.d(TAG, (entry.getUid().toString()) + "----------------------------++");
        Navigation.findNavController(view).navigate(EntryListFragmentDirections.updateEntryAction(entry.getUid().toString()));
      });

    recyclerView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
      @Override
      public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        info.setContentDescription("Your custom description here");
      }
    });
      recyclerView.setContentDescription("Journal Entries List");
      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

      mJournalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
      mJournalViewModel.getAllEntries().observe(getViewLifecycleOwner(), adapter::setEntries);

    btnAddEntry = view.findViewById(R.id.btn_add_entry);
    btnAddEntry.setOnClickListener(v -> {
      //Implementing this using navigation by ID's since there are no arguments, so no Bundle overhead!
      Navigation.findNavController(view).navigate(R.id.addEntryAction);   // Change to safe args
    });
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_info) {
      Intent infoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jamesclear.com/atomic-habits"));
      startActivity(infoIntent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }



}