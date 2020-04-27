package com.vipromos.pursuit;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface PursuitDao {

    @Query("SELECT * FROM pursuits ORDER BY text")
    public List<Pursuit> getPursuit();

    @Query("SELECT * FROM pursuits WHERE completed = :finished ORDER BY updated DESC")
    public List<Pursuit> getPursuitsNewerFirst(int finished);

    @Query("SELECT * FROM pursuits WHERE completed = :done  ORDER BY updated ASC")
    public List<Pursuit> getPursuitsOlderFirst(int done);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insertPursuit(Pursuit pursuit);

    @Update
    public void updatePursuit(Pursuit pursuit);

    @Delete
    public void deletePursuit(Pursuit pursuit);
}