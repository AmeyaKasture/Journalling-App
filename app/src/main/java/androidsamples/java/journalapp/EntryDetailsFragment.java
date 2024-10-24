package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

  private Button mDateButton, mStartTimeButton, mEndTimeButton, mSaveButton;
  private Calendar mStartTimeCalendar, mEndTimeCalendar, mDateCalendar;
  private JournalViewModel mJournalViewModel;
  private EditText mTitleEditText;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_entry_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Initialize ViewModel
    mJournalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);

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
    JournalEntry newEntry = new JournalEntry(title, formattedStartTime, formattedEndTime, formattedDate);

    // Insert the journal entry using the ViewModel
    mJournalViewModel.insert(newEntry);

    // Show a confirmation message
    Toast.makeText(getContext(), "Journal entry saved", Toast.LENGTH_SHORT).show();
    NavDirections action = EntryDetailsFragmentDirections.saveEntry();
    Navigation.findNavController(requireView()).navigate(action);
  }
}
