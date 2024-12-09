package androidsamples.java.journalapp.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidsamples.java.journalapp.R;

public class JournalEntryListAdapter extends RecyclerView.Adapter<JournalEntryListAdapter.EntryViewHolder> {
    private final LayoutInflater mInflator;
    private List<JournalEntry> mEntries;
    private final OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(JournalEntry entry);
    }

    public JournalEntryListAdapter(Context context, OnItemClickListener listener){
        mInflator = LayoutInflater.from(context);
        this.listener = listener;

    }

    @NotNull @Override
    public EntryViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType){
        View itemView = mInflator.inflate(R.layout.fragment_entry, parent, false);
        return new EntryViewHolder(itemView);
    }

    @Override
    public int getItemCount(){
        return (mEntries == null) ? 0 : mEntries.size();
    }

    public void setEntries(List<JournalEntry> entries){
        mEntries = entries;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NotNull EntryViewHolder holder, int position){
        if(mEntries != null){
            JournalEntry current = mEntries.get(position);
            holder.mTxtTitle.setText(current.getTitle());
            holder.mTxtDate.setText(current.getDate());
            holder.mTxtStartTime.setText(current.getStartTime());
            holder.mTxtEndTime.setText(current.getEndTime());

            if (listener != null) holder.itemView.setOnClickListener(v -> listener.onItemClick(current));

            holder.mTxtTitle.setContentDescription("Title: " + current.getTitle());
            holder.mTxtDate.setContentDescription("Date: " + current.getDate());
            holder.mTxtStartTime.setContentDescription("Start Time: " + current.getStartTime());
            holder.mTxtEndTime.setContentDescription("End Time: " + current.getEndTime());
        }
    }

    class EntryViewHolder extends RecyclerView.ViewHolder{
        private final TextView mTxtTitle;
        private final TextView mTxtDate;
        private final TextView mTxtStartTime;
        private final TextView mTxtEndTime;

        public EntryViewHolder(@NotNull View itemView){
            super(itemView);
            mTxtTitle = itemView.findViewById(R.id.txt_item_title);
            mTxtDate = itemView.findViewById(R.id.txt_item_date);
            mTxtStartTime = itemView.findViewById(R.id.txt_item_start_time);
            mTxtEndTime = itemView.findViewById(R.id.txt_item_end_time);
        }
    }
}
