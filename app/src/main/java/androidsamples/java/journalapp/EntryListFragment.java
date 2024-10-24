package androidsamples.java.journalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
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
  @Override
  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.fragment_entry_list, container, false);
    FloatingActionButton addEntryButton = view.findViewById(R.id.btn_add_entry);

    addEntryButton.setOnClickListener(v -> {
      // Create the action
      NavDirections action = EntryListFragmentDirections.addEntryAction();

      // Navigate using the action
      Navigation.findNavController(view).navigate(action);
    });

    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    JournalEntryListAdapter adapter = new JournalEntryListAdapter(view.getContext());
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

    mJournalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);

    // Use getViewLifecycleOwner() to observe LiveData safely
    mJournalViewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> {
      // Update the adapter when the data changes
      adapter.setEntries(entries);
    });

    return view;
  }

}