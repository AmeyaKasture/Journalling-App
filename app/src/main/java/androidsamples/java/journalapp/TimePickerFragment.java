package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class TimePickerFragment extends DialogFragment {

  private SharedViewModel sVM;

//  @NonNull
//  public static TimePickerFragment newInstance(Date time) {
//    // TODO implement the method
//    return null;
//  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    // TODO implement the method
    return new TimePickerDialog(requireContext(), (tp, hm, m)->{
      sVM.setTime(hm, m);
    }, 0, 0, false);
  }
}
