package androidsamples.java.journalapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JournalEntryListAdapter
        extends RecyclerView.Adapter<JournalEntryListAdapter.EntryViewHolder> {


    private final LayoutInflater mInflater;
    private List<JournalEntry> mEntries;

    public JournalEntryListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                              int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_entry,
                parent,
                false);
        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        if (mEntries != null) {
            JournalEntry current = mEntries.get(position);
            holder.mEntry = current;

            // Set text for each field
            holder.mTxtTitle.setText(current.getTitle());
            holder.mTxtStartTime.setText(current.getStartTime());
            holder.mTxtEndTime.setText(current.getEndTime());
            holder.mTxtDate.setText(current.getDate());

            // Set content descriptions for accessibility
            holder.mTxtTitle.setContentDescription("Title: " + current.getTitle());
            holder.mTxtStartTime.setContentDescription("Start Time: " + current.getStartTime());
            holder.mTxtEndTime.setContentDescription("End Time: " + current.getEndTime());
            holder.mTxtDate.setContentDescription("Date: " + current.getDate());

            // Optionally, set a comprehensive description on the itemView
            holder.itemView.setContentDescription("Journal entry titled " + current.getTitle()
                    + ", starts at " + current.getStartTime() + ", ends at " + current.getEndTime()
                    + ", on date " + current.getDate());
        }
    }
    @Override
    public int getItemCount() {
        if (mEntries != null) {
            return mEntries.size();
        } else {
            return 0;
        }
    }

    public void setEntries(List<JournalEntry> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

    class EntryViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTxtTitle;
        private final TextView mTxtStartTime;
        private final TextView mTxtEndTime;
        private final TextView mTxtDate;
        private JournalEntry mEntry;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtTitle = itemView.findViewById(R.id.txt_item_title);
            mTxtStartTime = itemView.findViewById(R.id.txt_item_start_time);
            mTxtEndTime = itemView.findViewById(R.id.txt_item_end_time);
            mTxtDate = itemView.findViewById(R.id.txt_item_date);

            itemView.setOnClickListener(this::launchJournalEntryFragment);
        }

        private void launchJournalEntryFragment(View v) {
            final String TAG="crzz";
            Log.d(TAG, "launchJournalEntryFragment with Entry: " + mEntry.getTitle());
            EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
            action.setEntryId(mEntry.getUid().toString());

            Navigation.findNavController(v).navigate(action);
        }
    }




}
