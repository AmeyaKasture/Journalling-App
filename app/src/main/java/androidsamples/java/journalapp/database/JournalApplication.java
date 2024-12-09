package androidsamples.java.journalapp.database;

import android.app.Application;

public class JournalApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        JournalRepository.init(this);
    }
}
