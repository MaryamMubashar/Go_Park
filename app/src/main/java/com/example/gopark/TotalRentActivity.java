package com.example.gopark;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TotalRentActivity extends AppCompatActivity {
    private TextView reservedSlotTextView, entryTimeTextView, exitTimeTextView, totalRentTextView;
    private String slotId;
    private long entryTime, exitTime, totalRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_rent);

        reservedSlotTextView = findViewById(R.id.reservedslot);
        entryTimeTextView = findViewById(R.id.entrytime);
        exitTimeTextView = findViewById(R.id.exittime);
        totalRentTextView = findViewById(R.id.totalrent);

        // Get data from intent
        slotId = getIntent().getStringExtra("slot_id");
        entryTime = getIntent().getLongExtra("entry_time", -1);
        exitTime = getIntent().getLongExtra("exit_time", -1);
        totalRent = getIntent().getLongExtra("total_rent", 0);

        // Update UI with the passed data
        reservedSlotTextView.setText(slotId);
        entryTimeTextView.setText(formatTime(entryTime));
        exitTimeTextView.setText(formatTime(exitTime));
        totalRentTextView.setText(totalRent + " Rs");
    }

    private String formatTime(long timeInMillis) {
        // Format the time in a readable format
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
        return dateFormat.format(new java.util.Date(timeInMillis));
    }
}
