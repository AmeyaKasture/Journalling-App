package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {

  private static final String ARG_DATE = "date";
  private DatePickerDialog.OnDateSetListener mListener;

  // Factory method to create a new instance of DatePickerFragment
  @NonNull
  public static DatePickerFragment newInstance(Date date, DatePickerDialog.OnDateSetListener listener) {
    DatePickerFragment fragment = new DatePickerFragment();
    fragment.mListener = listener;

    // Pass the date as an argument to the fragment
    Bundle args = new Bundle();
    args.putLong(ARG_DATE, date.getTime());
    fragment.setArguments(args);

    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    // Retrieve the date passed as an argument
    Date date = new Date();
    if (getArguments() != null) {
      long dateMillis = getArguments().getLong(ARG_DATE);
      date = new Date(dateMillis);
    }

    // Initialize Calendar with the given date
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    // Create and return the DatePickerDialog
    DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), mListener, year, month, day);

    // Set TalkBack description for the DatePicker dialog
    datePickerDialog.setTitle("Select date"); // Title can be read by TalkBack

    return datePickerDialog;
  }
}
