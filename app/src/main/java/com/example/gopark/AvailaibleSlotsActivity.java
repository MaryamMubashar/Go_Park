package com.example.gopark;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gopark.adapter.SlotsAdapter;
import com.example.gopark.apis.APIClient;
import com.example.gopark.apis.APIInterface;
import com.example.gopark.apis.Apiresponse;
import com.example.gopark.data.Slot;
import com.example.gopark.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailaibleSlotsActivity extends AppCompatActivity {
    private static final int BOOK_SLOT_REQUEST_CODE = 1;
    private APIInterface parkingApi;
    private RecyclerView recyclerView;
    private SlotsAdapter slotsAdapter;
    private List<Slot> slotsList = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availaible_slots);

        db = AppDatabase.getInstance(getApplicationContext());

        recyclerView = findViewById(R.id.availablerecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Retrofit API
        parkingApi = APIClient.getClient().create(APIInterface.class);

        // Fetch available slots
        getAvailableSlots();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BOOK_SLOT_REQUEST_CODE && resultCode == RESULT_OK) {
            String slotId = data.getStringExtra("slot_id");
            long entryTime = data.getLongExtra("entry_time", -1);

            if (slotId != null && entryTime != -1) {
                updateSlotAsBooked(slotId, entryTime);
            }
        }
    }
    private void getAvailableSlots() {
        parkingApi.getAvailableSlots().enqueue(new Callback<List<Slot>>() {
            @Override
            public void onResponse(Call<List<Slot>> call, Response<List<Slot>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    slotsList.clear();
                    slotsList.addAll(response.body());
                    for (Slot slot : slotsList) {
                        Log.d("SlotData", "Slot ID: " + slot.getSlotId() + ", Occupied: " + slot.isOccupied());
                    }
                    slotsAdapter = new SlotsAdapter(AvailaibleSlotsActivity.this, slotsList);
                    recyclerView.setAdapter(slotsAdapter);
                } else {
                    Toast.makeText(AvailaibleSlotsActivity.this, "Failed to fetch slots", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Slot>> call, Throwable t) {
                Toast.makeText(AvailaibleSlotsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Error fetching slots", t);
            }
        });
    }


    private void updateSlotAsBooked(String slotId, long entryTime) {
        AsyncTask.execute(() -> {
            db.slotDao().insertSlot(new Slot(slotId, entryTime, true));
        });

        for (Slot slot : slotsList) {
            if (slot.getSlotId().equals(slotId)) {
                slot.setOccupied(true);
                break;
            }
        }
        slotsAdapter.notifyDataSetChanged();
    }
}
