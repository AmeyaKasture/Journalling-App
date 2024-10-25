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
  @Override
  public void onCreate(Bundle savedInstanceState) {
    mJournalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
    setHasOptionsMenu(true);
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

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

}