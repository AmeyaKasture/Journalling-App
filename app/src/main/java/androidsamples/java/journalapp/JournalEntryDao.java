package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
public interface JournalEntryDao {
    @Insert
    void insert(JournalEntry entry);


    @Query("SELECT * FROM journal_table ORDER BY " +
            "substr(date, length(date) - 3, 4) || '-' || " +
            "(CASE " +
            "WHEN instr(date, 'January') > 0 THEN '01' " +
            "WHEN instr(date, 'February') > 0 THEN '02' " +
            "WHEN instr(date, 'March') > 0 THEN '03' " +
            "WHEN instr(date, 'April') > 0 THEN '04' " +
            "WHEN instr(date, 'May') > 0 THEN '05' " +
            "WHEN instr(date, 'June') > 0 THEN '06' " +
            "WHEN instr(date, 'July') > 0 THEN '07' " +
            "WHEN instr(date, 'August') > 0 THEN '08' " +
            "WHEN instr(date, 'September') > 0 THEN '09' " +
            "WHEN instr(date, 'October') > 0 THEN '10' " +
            "WHEN instr(date, 'November') > 0 THEN '11' " +
            "WHEN instr(date, 'December') > 0 THEN '12' " +
            "END) || '-' || " +
            "substr(date, length(date) - 7, 2) DESC")
    LiveData<List<JournalEntry>> getAllEntries();

    @Query("SELECT * from journal_table WHERE id=(:id)")
    LiveData<JournalEntry> getEntry(UUID id);

    @Update
    void update(JournalEntry entry);

    @Delete
    void delete(JournalEntry entry);


}
