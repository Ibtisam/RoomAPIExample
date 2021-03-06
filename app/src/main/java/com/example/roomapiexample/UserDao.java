package com.example.roomapiexample;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE username LIKE :name")
    User findByName(String name);

    @Insert
    void insertAll(User... users);

    @Insert
    void insertOne(User user);

    @Delete
    void delete(User user);
}
