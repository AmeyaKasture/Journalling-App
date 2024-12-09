package androidsamples.java.journalapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JournalRepository {
    private static final String DATABASE = "journal_table";
    private final JournalEntryDAO mJournalEntryDAO;
    private static JournalRepository sInstance;
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    private JournalRepository(Context context){
        JournalRoomDatabase db = Room.databaseBuilder(context.getApplicationContext(), JournalRoomDatabase.class, DATABASE).build();
        mJournalEntryDAO = db.journalEntryDAO();
    }

    public static void init(Context context){
        if(sInstance == null) sInstance = new JournalRepository(context);
    }

    public static JournalRepository getInstance(){
        if(sInstance == null) throw new IllegalStateException("Repo. must be initialised");
        return sInstance;
    }

    public void insert(JournalEntry entry){
        mExecutor.execute(() -> mJournalEntryDAO.insert(entry));
    }

    public void update(JournalEntry entry){
        mExecutor.execute(() -> mJournalEntryDAO.update(entry));
    }

    public void delete(JournalEntry entry){
        mExecutor.execute(() -> mJournalEntryDAO.delete(entry));
    }

    public JournalEntry getEntryById(UUID uuid){
        Future<JournalEntry> futureEntry = mExecutor.submit(() -> mJournalEntryDAO.getEntryById(uuid));
        try {
            return futureEntry.get(); // This will block until the result is available
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle exceptions appropriately
        }
    }

    public LiveData<List<JournalEntry>> getAllEntries(){
        return mJournalEntryDAO.getAllEntries();
    }

}
