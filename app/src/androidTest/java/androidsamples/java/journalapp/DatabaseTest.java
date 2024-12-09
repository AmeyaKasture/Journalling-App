package androidsamples.java.journalapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import androidsamples.java.journalapp.database.JournalEntry;
import androidsamples.java.journalapp.database.JournalEntryDAO;
import androidsamples.java.journalapp.database.JournalRoomDatabase;

public class DatabaseTest {

  // Rule for LiveData to run instantly
  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private JournalRoomDatabase db;
  private JournalEntryDAO journalEntryDao;

  @Before
  public void setUp() {
    db = Room.inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(), // Use the application context here
                    JournalRoomDatabase.class
            )
            .allowMainThreadQueries() // Only for testing
            .build();
    journalEntryDao = db.journalEntryDAO();
  }

  @After
  public void tearDown() {
    if (db != null) {
      db.close();
      db = null; // Clear reference after closing to avoid accidental reuse
    }
  }

  @Test
  public void testCRUDOperations() throws Exception {
    // Create and Insert an Entry
    JournalEntry entry = new JournalEntry("Meeting", "10:00 AM", "11:00 AM", "2024-10-30");
    UUID entryId = entry.getUid();

    journalEntryDao.insert(entry);

    // Read the Entry
    JournalEntry retrievedEntry = journalEntryDao.getEntryById(entryId);
    assertEquals("Meeting", retrievedEntry.getTitle());
    assertEquals("10:00 AM", retrievedEntry.getStartTime());
    assertEquals("11:00 AM", retrievedEntry.getEndTime());
    assertEquals("2024-10-30", retrievedEntry.getDate());

    // Update the Entry
    retrievedEntry.setTitle("Updated Meeting");
    retrievedEntry.setStartTime("09:00 AM");
    retrievedEntry.setEndTime("10:00 AM");
    journalEntryDao.update(retrievedEntry);

    JournalEntry updatedEntry = journalEntryDao.getEntryById(entryId);
    assertEquals("Updated Meeting", updatedEntry.getTitle());
    assertEquals("09:00 AM", updatedEntry.getStartTime());
    assertEquals("10:00 AM", updatedEntry.getEndTime());

    // Delete the Entry
    journalEntryDao.delete(updatedEntry);

    JournalEntry deletedEntry = journalEntryDao.getEntryById(entryId);
    assertNull(deletedEntry);
  }
}