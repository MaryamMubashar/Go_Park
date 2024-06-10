package com.example.gopark.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gopark.data.Slot;

import java.util.List;

@Dao
public interface SlotDao {
    @Query("SELECT * FROM slots")
    List<Slot> getAllSlots();

    @Query("SELECT * FROM slots WHERE isOccupied = 1")
    List<Slot> getOccupiedSlots();

    @Insert
    void insertSlot(Slot slot);

}
