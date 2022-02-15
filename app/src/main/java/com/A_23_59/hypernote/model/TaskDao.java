package com.A_23_59.hypernote.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
     long addTask(Task task);

    @Delete
    int deleteTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("SELECT * FROM tbl_task")
    List<Task> getTasks();

    @Query("SELECT * FROM tbl_task WHERE taskTitle LIKE '%' || :query || '%' ")
    List<Task> searchResult(String query);

    @Query("DELETE FROM tbl_task")
    void clearAll();



}
