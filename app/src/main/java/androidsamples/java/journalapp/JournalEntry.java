package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "journal_table")
@TypeConverters({JournalTypeConverters.class})  // Add the converter here
public class JournalEntry {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private UUID mUid;  // Using UUID type now

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "start_time")
    private String mStartTime;

    @ColumnInfo(name = "end_time")
    private String mEndTime;

    @ColumnInfo(name = "date")
    private String mDate;

    // Constructor
    public JournalEntry(@NonNull String mTitle, String mStartTime, String mEndTime, String mDate) {
        this.mUid = UUID.randomUUID();  // Generate UUID without converting to String
        this.mTitle = mTitle;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mDate = mDate;
    }

    // Empty constructor (required by Room)
    public JournalEntry() {
        this.mUid = UUID.randomUUID();  // Generate new UUID by default
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

    // Getter for mStartTime
    public String getStartTime() {
        return mStartTime;
    }

    // Setter for mStartTime
    public void setStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    // Getter for mEndTime
    public String getEndTime() {
        return mEndTime;
    }

    // Setter for mEndTime
    public void setEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    // Getter for mDate
    public String getDate() {
        return mDate;
    }

    // Setter for mDate
    public void setDate(String mDate) {
        this.mDate = mDate;
    }
}
