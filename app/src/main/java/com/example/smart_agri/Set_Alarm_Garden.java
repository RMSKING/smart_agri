package com.example.smart_agri;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Set_Alarm_Garden extends AppCompatActivity {

    private TextView selectTime;
    private TimePicker alarmTimePicker;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm_garden);

        selectTime = (TextView) findViewById(R.id.selectTime);
        alarmTimePicker =  findViewById(R.id.alarmTimePicker);
        alarmTimePicker.setIs24HourView(false);

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Set_Alarm_Garden.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                calendar.set(Calendar.SECOND, 0);

                                // Display selected time in your TextView
                                selectTime.setText(String.format("%02d:%02d", hourOfDay, minute));

                                // Set the alarm
                                setAlarm(calendar);
                            }
                        }, hour, minute, false);

                timePickerDialog.show();
            }
        });
    }

    private void setAlarm(Calendar calendar) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Set_Alarm_Garden.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(Set_Alarm_Garden.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm to trigger at the specified time
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }
}
