package com.company.p4;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;

    ToggleButton toggleButton;
    Switch aSwitch;
    CheckBox checkBox;
    CheckedTextView checkedTextView;
    RadioGroup radioGroup;
    Spinner spinner1, spinner2;
    ProgressBar progressBar1, progressBar2;
    Button startProgress;
    SeekBar seekBar1, seekBar2;
    RatingBar ratingBar;
    TimePicker timePicker1, timePicker2;
    DatePicker datePicker1, datePicker2;
    CalendarView calendarView;
    NumberPicker numberPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getPreferences(MODE_PRIVATE);
        prefs.edit().clear().apply();


        // ------- TOGGLE BUTTON

        toggleButton = findViewById(R.id.toogleButton);
        toggleButton.setChecked(prefs.getBoolean("TOGGLEBUTTON", false));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prefs.edit().putBoolean("TOGGLEBUTTON", b).apply();
            }
        });


        // ------- SWITCH

        aSwitch = findViewById(R.id.aSwitch);
        aSwitch.setChecked(prefs.getBoolean("SWITCH", false));
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prefs.edit().putBoolean("SWITCH", b).apply();
            }
        });


        // ------- CHECKBOX

        checkBox = findViewById(R.id.checkBox);
        checkBox.setChecked(prefs.getBoolean("CHECKBOX", false));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prefs.edit().putBoolean("CHECKBOX", b).apply();
            }
        });


        // ------- CHECKEDTEXTVIEW

        checkedTextView = findViewById(R.id.checkedTextView);
        checkedTextView.setChecked(prefs.getBoolean("CHECKEDTEXTVIEW",false));
        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedTextView.toggle();
                prefs.edit().putBoolean("CHECKEDTEXTVIEW", checkedTextView.isChecked()).apply();
            }
        });


        // ------- RADIOGROUP

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(prefs.getInt("RADIOGROUP", radioGroup.getCheckedRadioButtonId()));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                prefs.edit().putInt("RADIOGROUP", i).apply();
            }
        });


        // ------- SPINNER

        spinner1 = findViewById(R.id.spinner1);
        spinner1.setSelection(prefs.getInt("SPINNER1", -1));
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prefs.edit().putInt("SPINNER1", i).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2 = findViewById(R.id.spinner2);
        String[] items = new String[]{"Milky Way", "Andromeda", "Cassiopeia", "Sagittarius", "Pegasus"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items);
        spinner2.setAdapter(arrayAdapter);
        spinner2.setPrompt("Choose your galaxy");
        spinner2.setSelection(prefs.getInt("SPINNER2", 0));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prefs.edit().putInt("SPINNER2", i).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // ------- PROGRESSBAR

        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        startProgress = findViewById(R.id.startProgress);

        progressBar1.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);

        progressBar2.setMax(100);
        progressBar2.setProgress(0);

        startProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Integer, Integer, String>() {
                    @Override
                    protected String doInBackground(Integer... integers) {
                        int total = integers[0];
                        int step = integers[1];
                        for (int count=0; count <= total/step; count++) {
                            try {
                                Thread.sleep(step);
                                publishProgress((int) (count*step/(float)total*100));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return "Task Completed.";
                    }
                    @Override
                    protected void onPreExecute() {
                        startProgress.setText("Task Starting...");
                        startProgress.setEnabled(false);
                        progressBar1.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.VISIBLE);

                    }
                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        progressBar2.setProgress((int) (values[0]*progressBar2.getMax()/100.0f));
                    }
                    @Override
                    protected void onPostExecute(String result) {
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                        startProgress.setText("Restart");
                        startProgress.setEnabled(true);
                        progressBar1.setVisibility(View.GONE);
                        progressBar2.setVisibility(View.GONE);
                    }
                }.execute(1000, 10);
            }
        });


        // ------- SEEKBAR

        seekBar1 = findViewById(R.id.seekBar1);
        seekBar1.setProgress(prefs.getInt("SEEKBAR1",0));
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                prefs.edit().putInt("SEEKBAR1", i).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar2 = findViewById(R.id.seekBar2);
        seekBar2.setProgress(prefs.getInt("SEEKBAR2",0));
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                prefs.edit().putInt("SEEKBAR2", i).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // ------- RATINGBAR

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(prefs.getFloat("RATINGBAR", 0));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                prefs.edit().putFloat("RATINGBAR", v);
            }
        });


        // ------- TIMEPICKER

        timePicker1 = findViewById(R.id.timePicker1);
        timePicker1.setHour(getPreferences(MODE_PRIVATE).getInt("TIMEPICKER1_HOUR", 0));
        timePicker1.setMinute(getPreferences(MODE_PRIVATE).getInt("TIMEPICKER1_MINUTE", 0));
        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                prefs.edit()
                        .putInt("TIMEPICKER1_HOUR", i)
                        .putInt("TIMEPICKER1_MINUTE", i1)
                        .apply();
            }
        });

        timePicker2 = findViewById(R.id.timePicker2);
        timePicker2.setHour(getPreferences(MODE_PRIVATE).getInt("TIMEPICKER2_HOUR", 0));
        timePicker2.setMinute(getPreferences(MODE_PRIVATE).getInt("TIMEPICKER2_MINUTE", 0));
        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                prefs.edit()
                        .putInt("TIMEPICKER2_HOUR", i)
                        .putInt("TIMEPICKER2_MINUTE", i1)
                        .apply();
            }
        });


        // ------- DATEPICKER

        datePicker1 = findViewById(R.id.datePicker1);
        datePicker1.updateDate(
                prefs.getInt("DATEPICKER1_YEAR", datePicker1.getYear()),
                prefs.getInt("DATEPICKER1_MONTH", datePicker1.getMonth()),
                prefs.getInt("DATEPICKER1_DAYOFMONTH", datePicker1.getDayOfMonth()));
        datePicker1.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                prefs.edit()
                        .putInt("DATEPICKER1_YEAR", i)
                        .putInt("DATEPICKER1_MONTH", i1)
                        .putInt("DATEPICKER1_DAYOFMONTH", i2)
                        .apply();
            }
        });

        datePicker2 = findViewById(R.id.datePicker2);
        datePicker2.updateDate(
                prefs.getInt("DATEPICKER2_YEAR", datePicker2.getYear()),
                prefs.getInt("DATEPICKER2_MONTH", datePicker2.getMonth()),
                prefs.getInt("DATEPICKER2_DAYOFMONTH", datePicker2.getDayOfMonth()));
        datePicker2.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                prefs.edit()
                        .putInt("DATEPICKER2_YEAR", i)
                        .putInt("DATEPICKER2_MONTH", i1)
                        .putInt("DATEPICKER2_DAYOFMONTH", i2)
                        .apply();
            }
        });


        // ------- CALENDARVIEW

        calendarView = findViewById(R.id.calendarView);
        calendarView.setDate(prefs.getLong("CALENDARVIEW", Calendar.getInstance().getTimeInMillis()));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar.getInstance().set(i,i1,i2);
                prefs.edit().putLong("CALENDARVIEW", Calendar.getInstance().getTimeInMillis()).apply();
            }
        });


        // ------- NUMBERPICKER

        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(prefs.getInt("NUMBERPICKER", numberPicker.getMinValue()));
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                prefs.edit().putInt("NUMBERPICKER", i1).apply();
            }
        });
    }
}
