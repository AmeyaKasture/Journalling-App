package androidsamples.java.journalapp.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.UUID;

public class JournalViewModel extends ViewModel {
    private final JournalRepository mRepository;

    public JournalViewModel(){
        mRepository = JournalRepository.getInstance();
    }

    public LiveData<List<JournalEntry>> getAllEntries(){
        return mRepository.getAllEntries();
    }

    public void insert(JournalEntry entry){
        mRepository.insert(entry);
    }

    public JournalEntry getEntryById(UUID uuid){
        return mRepository.getEntryById(uuid);
    }

    public void update(JournalEntry entry){
        mRepository.update(entry);
    }

    public void delete(JournalEntry entry){
        mRepository.delete(entry);
    }
}
