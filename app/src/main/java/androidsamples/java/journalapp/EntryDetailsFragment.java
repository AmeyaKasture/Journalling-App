package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class EntryDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

  private Button mDateButton, mStartTimeButton, mEndTimeButton, mSaveButton;
  private Calendar mStartTimeCalendar, mEndTimeCalendar, mDateCalendar;
  private EntryDetailsViewModel mEntryDetailsViewModel;
  private EditText mTitleEditText;
  public final String TAG="tagger";
  private JournalEntry mEntry;




  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

    mEntryDetailsViewModel = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);

    UUID entryId = UUID.fromString(EntryDetailsFragmentArgs.fromBundle(getArguments()).getEntryId());
    Log.d(TAG, "Loading entry: " + entryId);
    mEntryDetailsViewModel.getEntryLiveData().observe(requireActivity(),
            entry -> {
              this.mEntry= entry;
              if (entry != null) updateUI();
            });
    mEntryDetailsViewModel.loadEntry(entryId);
  }

  private void updateUI() {
    mTitleEditText.setText(mEntry.getTitle());

    if (!mEntry.getDate().isEmpty()) {
      mDateButton.setText(mEntry.getDate());
    } else {
      mDateButton.setText("Date");
    }

    if (!mEntry.getStartTime().isEmpty()) {
      mStartTimeButton.setText(mEntry.getStartTime());
    }
    else {
      mStartTimeButton.setText("Start Time");
    }

    if (!mEntry.getEndTime().isEmpty()) {
      mEndTimeButton.setText(mEntry.getEndTime());
    }
    else {
      mEndTimeButton.setText("End Time");
    }

  }
  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_entry_detail, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.delete) {
      Log.d(TAG, "Delete button clicked");

      new AlertDialog.Builder(requireActivity())
              .setTitle("Delete Entry")
              .setMessage("This entry will be deleted. Proceed?")
              .setIcon(android.R.drawable.ic_menu_delete)
              .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                mEntryDetailsViewModel.deleteEntry(mEntry);
                requireActivity().onBackPressed();
              })
              .setNegativeButton(android.R.string.no, null).show();

    }

    else if (item.getItemId() == R.id.share) {
      Log.d(TAG, "Share button clicked");

      Intent sendIntent = new Intent();
      sendIntent.setAction(Intent.ACTION_SEND);
      String text = "Look what I have been up to: " + mEntry.getTitle() + " on " + mEntry.getDate() + ", " + mEntry.getStartTime() + " to " + mEntry.getEndTime();
      sendIntent.putExtra(Intent.EXTRA_TEXT, text);
      sendIntent.setType("text/plain");
      Intent.createChooser(sendIntent,"Share via");
      startActivity(sendIntent);
    }

    return super.onOptionsItemSelected(item);
  }
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_entry_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Initialize ViewModel
    mEntryDetailsViewModel = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);

    // Initialize UI components
    mDateButton = view.findViewById(R.id.btn_entry_date);
    mStartTimeButton = view.findViewById(R.id.btn_start_time);
    mEndTimeButton = view.findViewById(R.id.btn_end_time);
    mSaveButton = view.findViewById(R.id.btn_save);
    mTitleEditText = view.findViewById(R.id.edit_title);

    mStartTimeCalendar = Calendar.getInstance();
    mEndTimeCalendar = Calendar.getInstance();
    mDateCalendar = Calendar.getInstance();

    // Date picker for the date button
    mDateButton.setOnClickListener(v -> {
      DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new Date(), this);
      datePickerFragment.show(getParentFragmentManager(), "datePicker");
    });

    // Time picker for the start time button
    mStartTimeButton.setOnClickListener(v -> {
      TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(new Date(), (view1, hourOfDay, minute) -> {
        mStartTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mStartTimeCalendar.set(Calendar.MINUTE, minute);
        updateButtonTime(mStartTimeButton, mStartTimeCalendar, "Start Time");
      });
      timePickerFragment.show(getParentFragmentManager(), "startTimePicker");
    });

    // Time picker for the end time button with sanity check
    mEndTimeButton.setOnClickListener(v -> {
      TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(new Date(), (view1, hourOfDay, minute) -> {
        mEndTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mEndTimeCalendar.set(Calendar.MINUTE, minute);
        updateButtonTime(mEndTimeButton, mEndTimeCalendar, "End Time");
      });
      timePickerFragment.show(getParentFragmentManager(), "endTimePicker");
    });

    // Save button onClickListener to perform the saving operation
    mSaveButton.setOnClickListener(v -> saveJournalEntry());
  }

  // This method will be called when a date is selected
  @Override
  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    mDateCalendar.set(year, month, dayOfMonth);
    Date selectedDate = mDateCalendar.getTime();

    // Format the selected date as "Day, Month Day, Year"
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.getDefault());
    String formattedDate = dateFormat.format(selectedDate);

    // Update the button text with the formatted date
    mDateButton.setText(formattedDate);
  }

  // Helper method to update the button text with the formatted time (hh:mm) and replace button text with "Start Time" or "End Time"
  private void updateButtonTime(Button button, Calendar calendar, String label) {
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    String formattedTime = timeFormat.format(calendar.getTime());

    // Set button text to the formatted time
    button.setText(formattedTime);
  }

  // Method to perform sanity checks and save the journal entry
  private void saveJournalEntry() {
    String title = mTitleEditText.getText().toString().trim(); // In a real app, you'd probably have an EditText for the title

    // Check if date, start time, and end time are set
    if (mDateButton.getText().toString().equals("Select Date") ||
            mStartTimeButton.getText().toString().equals("Start Time") ||
            mEndTimeButton.getText().toString().equals("End Time")) {
      Toast.makeText(getContext(), "Please select date, start time, and end time", Toast.LENGTH_SHORT).show();
      return;
    }

    // Sanity check: ensure end time is after start time
    if (mEndTimeCalendar.before(mStartTimeCalendar)) {
      Toast.makeText(getContext(), "End time must be after start time", Toast.LENGTH_SHORT).show();
      return;
    }

    // Format the date and times for storing
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.getDefault());
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    String formattedDate = dateFormat.format(mDateCalendar.getTime());
    String formattedStartTime = timeFormat.format(mStartTimeCalendar.getTime());
    String formattedEndTime = timeFormat.format(mEndTimeCalendar.getTime());

    // Create a new JournalEntry
    mEntry.setTitle(title);
    mEntry.setDate(formattedDate);
    mEntry.setStartTime(formattedStartTime);
    mEntry.setEndTime(formattedEndTime);

    // Insert the journal entry using the ViewModel
    mEntryDetailsViewModel.saveEntry(mEntry);
    Log.d(TAG,formattedDate);
    // Show a confirmation message
    Toast.makeText(getContext(), "Journal entry saved", Toast.LENGTH_SHORT).show();
    requireActivity().onBackPressed();
  }
}
