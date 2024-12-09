package androidsamples.java.journalapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.UUID;

import androidsamples.java.journalapp.database.JournalEntry;
import androidsamples.java.journalapp.database.JournalViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {

  private Button btnDate;
  private Button btnStartTime;
  private Button btnEndTime;
  private Button btnSave;
  private EditText mEditText;
  private SharedViewModel sVM;
  private JournalViewModel mJournalViewModel;
  private JournalEntry mEntry;
  private static final String TAG = "EntryDetailsFragment";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    mJournalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater){
    inflater.inflate(R.menu.entry_details_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_entry_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Initialising UI elements
    btnDate = view.findViewById(R.id.btn_entry_date);
    btnStartTime = view.findViewById(R.id.btn_start_time);
    btnEndTime = view.findViewById(R.id.btn_end_time);
    btnSave = view.findViewById(R.id.btn_save);
    mEditText = view.findViewById(R.id.edit_title);

    Bundle args = getArguments();
    JournalEntry entry;
    if(args != null && args.containsKey("entryId")){
      Log.d(TAG, "Hello----------------------------------"+EntryDetailsFragmentArgs.fromBundle(args).getEntryId());
      UUID entryId = UUID.fromString(EntryDetailsFragmentArgs.fromBundle(args).getEntryId());
      Log.d(TAG, entryId.toString());
      entry = mJournalViewModel.getEntryById(entryId);
      sVM.setDate(entry.getDate());
      sVM.setStartTime(entry.getStartTime());
      sVM.setEndTime(entry.getEndTime());
      mEditText.setText(entry.getTitle());
    }
    else{
      entry = null;
//      sVM.setStartTime("START TIME");
//      sVM.setEndTime("END TIME");
//      sVM.setDate("DATE");
    }
    mEntry = entry;

    updateUI();

    // Set an observer on the date field of the shared view model
    sVM.getLiveDate().observe(getViewLifecycleOwner(), date -> updateUI());
    sVM.getLiveStartTime().observe(getViewLifecycleOwner(), time -> updateUI());
    sVM.getLiveEndTime().observe(getViewLifecycleOwner(), time -> updateUI());

    // Setting listeners on the UI buttons
    btnDate.setOnClickListener(v -> {
      Navigation.findNavController(view).navigate(R.id.datePickerDialog);   // Change to safe args
    });

    btnStartTime.setOnClickListener(v -> {
      sVM.setTimeClicked(true);
      Navigation.findNavController(view).navigate(R.id.timePickerDialog);   // Change to safe args
    });

    btnEndTime.setOnClickListener(v -> {
      sVM.setTimeClicked(false);
      Navigation.findNavController(view).navigate(R.id.timePickerDialog);   // Change to safe args
    });

    btnSave.setOnClickListener(v -> {
      String title = mEditText.getText().toString().trim(); // In a real app, you'd probably have an EditText for the title
//    Log.d(TAG, "saveJournalEntry:"+mEndTimeButton.getText().toString().trim());
      // Check if date, start time, and end time are set
      if (title.equals("")||btnDate.getText().toString().equals("DATE") ||
              btnStartTime.getText().toString().equals("START TIME") ||
              btnEndTime.getText().toString().equals("END TIME")) {
        Toast.makeText(getContext(), "Please select date, start time, and end time", Toast.LENGTH_SHORT).show();
        return;
      }

      if(sVM.getLiveStartTime().getValue().equals("START TIME") || sVM.getLiveEndTime().getValue().equals("END TIME") || sVM.getLiveDate().getValue().equals("DATE")){
        Toast.makeText(getContext(), "You must select the date and time to proceed!", Toast.LENGTH_SHORT).show();
        return;
      }
      if(entry == null){
        JournalEntry toAdd = new JournalEntry(mEditText.getText().toString(), sVM.getLiveStartTime().getValue(), sVM.getLiveEndTime().getValue(), sVM.getLiveDate().getValue());
        mJournalViewModel.insert(toAdd);
      }
      else{
        entry.setDate(sVM.getLiveDate().getValue());
        entry.setStartTime(sVM.getLiveStartTime().getValue());
        entry.setEndTime(sVM.getLiveEndTime().getValue());
        entry.setTitle(mEditText.getText().toString());
        mJournalViewModel.update(entry);
      }
      sVM.setStartTime("START TIME");
      sVM.setEndTime("END TIME");
      sVM.setDate("DATE");
      Navigation.findNavController(view).navigate(R.id.entryListFragment);  // Change to safe args
    });
  }

  public void updateUI(){
    btnDate.setText(sVM.getLiveDate().getValue());
    btnStartTime.setText(sVM.getLiveStartTime().getValue());
    btnEndTime.setText(sVM.getLiveEndTime().getValue());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_delete) {
      Log.d(TAG, "Delete this========================");
      deleteEntry(getView());
      return true;
    }
    else if (id == R.id.action_share) {
      Log.d(TAG, "Share this========================");
      shareEntry();
      return true;
    }
      else {
      return super.onOptionsItemSelected(item);
    }
  }

  private void shareEntry() {
    Log.d(TAG, "Share this!========================");
    if(mEntry == null){
      Toast.makeText(getContext(), "You can't share an entry that is not created!", Toast.LENGTH_SHORT).show();
      return;
    }
    String title = mEntry.getTitle();
    String date = mEntry.getDate();
    String startTime = mEntry.getStartTime();
    String endTime = mEntry.getEndTime();

    String toShare = "Look what I have been up to: " + title + " on " + date + ", " + startTime + " to " + endTime;

    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_TEXT, toShare);

    startActivity(Intent.createChooser(shareIntent, "Share Text Via"));
  }

  private void deleteEntry(View view) {
    if(mEntry == null){
      Toast.makeText(getContext(), "You can't delete an entry that is not created!", Toast.LENGTH_SHORT).show();
      return;
    }
    new AlertDialog.Builder(getContext())
            .setTitle("Delete Entry")
            .setMessage("Are you sure you want to delete this entry?")
            .setPositiveButton("Yes", (dialog, which) -> {
              mJournalViewModel.delete(mEntry);
              dialog.dismiss();
              Navigation.findNavController(view).navigate(R.id.entryListFragment);  // Change to safe args
            })
            .setNegativeButton("No", (dialog, which) -> {
              dialog.dismiss();
            })
            .show();
  }
}