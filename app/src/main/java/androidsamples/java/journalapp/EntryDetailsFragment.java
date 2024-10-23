package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

  private Button mDateButton;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_entry_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mDateButton = view.findViewById(R.id.btn_entry_date);

    mDateButton.setOnClickListener(v -> {
      // Create a new instance of DatePickerFragment and show it
      DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new Date(), this);
      datePickerFragment.show(getParentFragmentManager(), "datePicker");
    });
  }

  // This method will be called when a date is selected
  @Override
  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    // Handle the selected date
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, dayOfMonth);
    Date selectedDate = calendar.getTime();

    // Format the selected date as "Day, Month Day, Year" (e.g., "Tuesday, October 24, 2024")
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.getDefault());
    String formattedDate = dateFormat.format(selectedDate);

    // Update the button text with the formatted date
    mDateButton.setText(formattedDate);
  }
}
