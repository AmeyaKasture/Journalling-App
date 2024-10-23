package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private UUID mUid;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "start_time")
    private String mstart_time;

    @ColumnInfo(name = "end_time")
    private String mend_time;

    @ColumnInfo(name = "date")
    private String mdate;


    public JournalEntry(@NonNull String title, String start_time,String end_time,String date) {
        mUid = UUID.randomUUID();
        mTitle = title;
        mstart_time=start_time;
        mend_time=end_time;
        mdate=date;
    }

// getters and setters
}
