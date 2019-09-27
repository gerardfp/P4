package com.company.p4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    TextClock textClock;
    DatePicker datePicker1, datePicker2;
    CalendarView calendarView;
    NumberPicker numberPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getPreferences(MODE_PRIVATE);

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
        spinner1.setSelection(prefs.getInt("SPINNER1", 0));
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
                    protected void onPostExecute(String result) {
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                        startProgress.setText("Restart");
                        startProgress.setEnabled(true);
                        progressBar1.setVisibility(View.GONE);
                        progressBar2.setVisibility(View.GONE);

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

        timePicker2 = findViewById(R.id.timePicker2);

        // ------- TEXTCLOCK

        textClock = findViewById(R.id.textClock);

        // ------- DATEPICKER
        datePicker1 = findViewById(R.id.datePicker1);
        datePicker2 = findViewById(R.id.datePicker2);

        // ------- CALENDARVIEW
        calendarView = findViewById(R.id.calendarView);

        // ------- NUMBERPICKER
        numberPicker = findViewById(R.id.numberPicker);
    }
}
