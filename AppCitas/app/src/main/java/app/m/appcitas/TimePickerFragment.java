package app.m.appcitas;

import android.app.TimePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import java.util.Calendar;
import android.text.format.DateFormat;

public class TimePickerFragment extends DialogFragment{

    private TimePickerDialog.OnTimeSetListener listener;

    public static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener listener) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(TimePickerDialog.OnTimeSetListener listener) {
        this.listener = listener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),listener, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}