package com.example.gopark.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "slots")
public class Slot {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("slot_id")
    private String slotId;

    @SerializedName("is_occupied")
    private boolean isOccupied;

    private long entryTime;

    public Slot(String slotId, long entryTime, boolean isOccupied) {
        this.slotId = slotId;
        this.entryTime = entryTime;
        this.isOccupied = isOccupied;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
