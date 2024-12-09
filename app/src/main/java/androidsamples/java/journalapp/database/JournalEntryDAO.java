package androidsamples.java.journalapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
public interface JournalEntryDAO {
    @Insert
    void insert(JournalEntry entry);

    @Update
    void update(JournalEntry entry);

    @Delete
    void delete(JournalEntry entry);

    @Query("SELECT * FROM journal_table WHERE id = :uuid LIMIT 1")
    JournalEntry getEntryById(UUID uuid);

    @Query("SELECT * FROM journal_table ORDER BY title ASC")
    LiveData<List<JournalEntry>> getAllEntries();
}
