package androidsamples.java.journalapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private UUID mUid;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "date")
    private String mDate;
    @ColumnInfo(name = "startTime")
    private String mStartTime;
    @ColumnInfo(name = "endTime")
    private String mEndTime;

    public JournalEntry(@NonNull String title, @NotNull String startTime, @NotNull String endTime, @NotNull String date){
        mUid = UUID.randomUUID();
        mTitle = title;
        mStartTime = startTime;
        mEndTime = endTime;
        mDate = date;
    }

    public void setTitle(String title){
        mTitle = title;
    }
    public void setStartTime(String time){
        mStartTime = time;
    }
    public void setEndTime(String time){
        mEndTime = time;
    }
    public void setDate(String date){
        mDate = date;
    }

    public String getTitle(){
        return mTitle;
    }
    public String getStartTime(){
        return mStartTime;
    }
    public String getEndTime(){
        return mEndTime;
    }
    public String getDate(){
        return mDate;
    }

    @NonNull
    public UUID getUid() {
        return mUid;
    }

    public void setUid(@NonNull UUID mUid) {
        this.mUid = mUid;
    }
}
