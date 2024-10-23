package androidsamples.java.journalapp;

import android.content.Context;

import androidx.room.Room;

public class JournalRepository {
    private static final String DATABASE_NAME = "journal_table";
    private final JournalEntryDao mJournalEntryDao;
    private static JournalRepository sInstance;
    private JournalRepository(Context context) {
        JournalRoomDatabase db
                = Room.databaseBuilder(context.getApplicationContext(),
                JournalRoomDatabase.class,
                DATABASE_NAME).build();
        mJournalEntryDao = db.journalEntryDao();
    }
    public static void init(Context context) {
        if (sInstance == null) sInstance = new JournalRepository(context);
    }

    public static JournalRepository getInstance() {
        if (sInstance == null)
            throw new IllegalStateException("Repo. must be initialized");
        return sInstance;
    }

}