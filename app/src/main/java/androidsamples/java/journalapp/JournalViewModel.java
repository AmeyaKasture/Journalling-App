package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class JournalViewModel extends ViewModel {
    private final JournalRepository mRepository;
    private final MutableLiveData<String> lastInsertedEntryId = new MutableLiveData<>();

    public JournalViewModel() {
        mRepository = JournalRepository.getInstance();
    }

    public LiveData<String> getLastInsertedEntryId() {
        return lastInsertedEntryId;
    }

    public void insert(JournalEntry entry) {
        mRepository.insert(entry, id -> lastInsertedEntryId.postValue(id.toString()));
    }

    // Call this to clear the lastInsertedEntryId after navigating
    public void clearLastInsertedEntryId() {
        lastInsertedEntryId.setValue(null);
    }

    public LiveData<List<JournalEntry>> getAllEntries() {
        return mRepository.getAllEntries();
    }
}

