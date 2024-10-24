package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {

  private static final String ARG_TIME = "time";
  private TimePickerDialog.OnTimeSetListener mListener;

  // Factory method to create a new instance of TimePickerFragment with the initial time passed as argument
  @NonNull
  public static TimePickerFragment newInstance(Date time, TimePickerDialog.OnTimeSetListener listener) {
    TimePickerFragment fragment = new TimePickerFragment();
    fragment.mListener = listener;

    Bundle args = new Bundle();
    args.putLong(ARG_TIME, time.getTime());
    fragment.setArguments(args);

    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    // Get the current time or the time passed as an argument
    Calendar calendar = Calendar.getInstance();
    if (getArguments() != null) {
      long timeInMillis = getArguments().getLong(ARG_TIME);
      calendar.setTime(new Date(timeInMillis));
    }

    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);

    // Create a new instance of TimePickerDialog and return it
    return new TimePickerDialog(requireContext(), mListener, hour, minute, true);
  }
}
