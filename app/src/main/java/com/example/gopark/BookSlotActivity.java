package com.example.gopark;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gopark.apis.APIClient;
import com.example.gopark.apis.APIInterface;
import com.example.gopark.apis.Apiresponse;
import com.example.gopark.data.Slot;
import com.example.gopark.room.AppDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookSlotActivity extends AppCompatActivity {
    private TextView suggestedZoneTextView, rentPerHourTextView;
    private Button confirmButton;
    private String slotId;
    private int rentPerHour;
    private APIInterface parkingApi;
    private AppDatabase db;
    private static final int INITIAL_RENT = 250; // First hour rent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_slot);

        suggestedZoneTextView = findViewById(R.id.textView5);
        rentPerHourTextView = findViewById(R.id.textViewee);
        confirmButton = findViewById(R.id.Bookspotbtn);

        db = AppDatabase.getInstance(this);

        // Get data from intent
        slotId = getIntent().getStringExtra("slot_id");
        rentPerHour = getIntent().getIntExtra("rent_per_hour", INITIAL_RENT);

        // Initialize Retrofit API
        parkingApi = APIClient.getClient().create(APIInterface.class);

        // Update UI with the passed data
        suggestedZoneTextView.setText("Zone: " + slotId);
        rentPerHourTextView.setText("Rent per hour: " + rentPerHour + " Rs");

        confirmButton.setOnClickListener(v -> confirmBooking(slotId));
    }

    private void confirmBooking(String slotId) {
        long entryTime = System.currentTimeMillis();
        parkingApi.bookSlot(slotId).enqueue(new Callback<Apiresponse>() {
            @Override
            public void onResponse(Call<Apiresponse> call, Response<Apiresponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Apiresponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        Toast.makeText(BookSlotActivity.this, "Slot booked successfully", Toast.LENGTH_SHORT).show();
                        // Save the booking to the local database
                        AsyncTask.execute(() -> {
                            db.slotDao().insertSlot(new Slot(slotId, entryTime, true));
                        });
                        // Charge the user for the first hour
                        chargeUser(INITIAL_RENT);
                        // Redirect to ReservedLaneActivity
                        Intent intent = new Intent(BookSlotActivity.this, ReservedLanesActivity.class);
                        intent.putExtra("slot_id", slotId);
                        intent.putExtra("entry_time", entryTime);
                        intent.putExtra("initial_rent", INITIAL_RENT);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(BookSlotActivity.this, "Failed to book slot", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BookSlotActivity.this, "Failed to book slot", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Apiresponse> call, Throwable t) {
                Toast.makeText(BookSlotActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void chargeUser(int amount) {
        // Implement charging logic here, e.g., integrate with a payment gateway
        Toast.makeText(this, "Charged: " + amount + " Rs", Toast.LENGTH_SHORT).show();
    }
}
