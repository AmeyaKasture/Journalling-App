package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JournalEntryDao {
    @Insert
    void insert(JournalEntry entry);

    @Query("SELECT * FROM journal_table ORDER BY substr(date, instr(date, ',') + 1) DESC")
    LiveData<List<JournalEntry>> getAllEntries();
}
