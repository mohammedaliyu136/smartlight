package com.mohammedaliyu.smartswitchlite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface LightDao {

    @Insert
    void insert(Light light);

    @Update
    void update(Light light);

    @Delete
    void delete(Light light);

    @Query("SELECT * FROM light_table")
    LiveData<List<Light>> getAll_Light();
}
