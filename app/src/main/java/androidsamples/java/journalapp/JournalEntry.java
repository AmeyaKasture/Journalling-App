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

    @ColumnInfo(name = "start_date")
    private Date mstart_date;

    @ColumnInfo(name = "end_date")
    private Date mend_date;


    public JournalEntry(@NonNull String title, Date start_date,Date end_date) {
        mUid = UUID.randomUUID();
        mTitle = title;
        mstart_date=start_date;
        mend_date=end_date;
    }

// getters and setters
}
