package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

  private Button mDateButton, mStartTimeButton, mEndTimeButton;
  private Calendar mStartTimeCalendar, mEndTimeCalendar;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_entry_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mDateButton = view.findViewById(R.id.btn_entry_date);
    mStartTimeButton = view.findViewById(R.id.btn_start_time);
    mEndTimeButton = view.findViewById(R.id.btn_end_time);

    // Initialize the start and end time calendars
    mStartTimeCalendar = Calendar.getInstance();
    mEndTimeCalendar = Calendar.getInstance();

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
      });
      timePickerFragment.show(getParentFragmentManager(), "endTimePicker");
    });
  }

  // This method will be called when a date is selected
  @Override
  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, dayOfMonth);
    Date selectedDate = calendar.getTime();

    // Format the selected date as "Day, Month Day, Year" (e.g., "Tuesday, October 24, 2024")
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.getDefault());
    String formattedDate = dateFormat.format(selectedDate);

    // Update the button text with the formatted date
    mDateButton.setText(formattedDate);
  }

  // Helper method to update the button text with the formatted time (hh:mm) and replace button text with "Start Time" or "End Time"
  private void updateButtonTime(Button button, Calendar calendar, String label) {
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    String formattedTime = timeFormat.format(calendar.getTime());

    // Set button text to the format: "<label>: <formattedTime>"
    button.setText( formattedTime);
  }
}
