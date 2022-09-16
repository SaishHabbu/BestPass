package com.example.bestpass;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PasswordDao {

    @Insert
    public  void insert(Password password);

    @Update
    public  void update(Password password);

    @Delete
    public  void delete(Password password);

    @Query("SELECT * FROM my_passwords")
    public LiveData<List<Password>> getAllData();
}
