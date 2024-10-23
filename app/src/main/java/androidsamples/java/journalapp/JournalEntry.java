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

    // Getter for mUid
    @NonNull
    public UUID getUid() {
        return mUid;
    }

    // Setter for mUid
    public void setUid(@NonNull UUID mUid) {
        this.mUid = mUid;
    }

    // Getter for mTitle
    public String getTitle() {
        return mTitle;
    }

    // Setter for mTitle
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    // Getter for mstart_time
    public String getStartTime() {
        return mstart_time;
    }

    // Setter for mstart_time
    public void setStartTime(String mstart_time) {
        this.mstart_time = mstart_time;
    }

    // Getter for mend_time
    public String getEndTime() {
        return mend_time;
    }

    // Setter for mend_time
    public void setEndTime(String mend_time) {
        this.mend_time = mend_time;
    }

    // Getter for mdate
    public String getDate() {
        return mdate;
    }

    // Setter for mdate
    public void setDate(String mdate) {
        this.mdate = mdate;
    }


}
