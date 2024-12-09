package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class DatePickerFragment extends DialogFragment {

  private SharedViewModel sVM;
//  @NonNull
//  public static DatePickerFragment newInstance(Date date) {
//    // TODO implement the method
//    return null;
//  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // TODO implement the method

    Calendar calendar = Calendar.getInstance();
    int curr_year = calendar.get(Calendar.YEAR);
    int curr_month = calendar.get(Calendar.MONTH);
    int curr_day = calendar.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(requireContext(), (dp, y, m, d) -> {
      calendar.set(y,m,d);
      SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault());
      sVM.setDate(sdf.format(calendar.getTime()));
    }, curr_year, curr_month, curr_day);
  }
}
