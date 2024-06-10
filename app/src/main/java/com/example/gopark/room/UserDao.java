package com.example.gopark.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gopark.data.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    User getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrReplace(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}
