package com.example.restaurant.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.restaurant.entities.Table;

import java.util.List;

@Dao
public interface TablesDao {

    @Query("SELECT * FROM tables")
    List<Table> getTables();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Table table);

}
