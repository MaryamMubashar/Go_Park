package com.example.gopark;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReservedLanesActivity extends AppCompatActivity {
    private TextView reservedSlotTextView, entryTimeTextView;
    private Button exitCalculateBtn;
    private String slotId;
    private long entryTime;
    private int initialRent;
    private static final int RENT_PER_HOUR = 250; // Additional hourly rent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserved_items);

        reservedSlotTextView = findViewById(R.id.reservedslot);
        entryTimeTextView = findViewById(R.id.entrytime);
        exitCalculateBtn = findViewById(R.id.exitcalculatebtn);

        // Get data from intent
        slotId = getIntent().getStringExtra("slot_id");
        entryTime = getIntent().getLongExtra("entry_time", -1);
        initialRent = getIntent().getIntExtra("initial_rent", RENT_PER_HOUR);

        // Update UI with the passed data
        reservedSlotTextView.setText(slotId);
        entryTimeTextView.setText(formatTime(entryTime));

        exitCalculateBtn.setOnClickListener(v -> calculateRent());
    }

    private void calculateRent() {
        long exitTime = System.currentTimeMillis();
        long durationInHours = (exitTime - entryTime) / (1000 * 60 * 60);
        long totalRent = initialRent + Math.max(0, durationInHours - 1) * RENT_PER_HOUR;

        // Redirect to TotalRentActivity
        Intent intent = new Intent(ReservedLanesActivity.this, TotalRentActivity.class);
        intent.putExtra("slot_id", slotId);
        intent.putExtra("entry_time", entryTime);
        intent.putExtra("exit_time", exitTime);
        intent.putExtra("total_rent", totalRent);
        startActivity(intent);
        finish();
    }

    private String formatTime(long timeInMillis) {
        // Format the time in a readable format
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
        return dateFormat.format(new java.util.Date(timeInMillis));
    }
}
