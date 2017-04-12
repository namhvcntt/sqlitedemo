package com.rikkeisoft.mobile.databasedemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rikkeisoft.mobile.databasedemo.R;
import com.rikkeisoft.mobile.databasedemo.callbacks.ListNoteInteractiveListener;
import com.rikkeisoft.mobile.databasedemo.model.Note;

import java.util.List;

/**
 * Adaptive list notes
 * Created by HoangVuNam on 4/12/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ListNoteInteractiveListener mListener;
    private List<Note> mNotes;

    public NoteAdapter(List<Note> notes, ListNoteInteractiveListener listener) {
        mNotes = notes;
        mListener = listener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, final int position) {
        final Note note = mNotes.get(position);
        holder.bindData(note);
        holder.itemView.findViewById(R.id.ivEditNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onUserSelectEditNote(note);
                }
            }
        });

        holder.itemView.findViewById(R.id.ivDeleteNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onUserSelectDeleteNote(note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == mNotes? 0 : mNotes.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNoteTitle;
        public TextView tvNoteContent;
        public TextView tvNoteUpdatedTime;
        public NoteViewHolder(View itemView) {
            super(itemView);
            tvNoteTitle = (TextView) itemView.findViewById(R.id.tvNoteTitle);
            tvNoteContent = (TextView) itemView.findViewById(R.id.tvNoteContent);
            tvNoteUpdatedTime = (TextView) itemView.findViewById(R.id.tvNoteUpdateTime);
        }

        public void bindData(Note note) {
            tvNoteTitle.setText(note.getTitle());
            tvNoteContent.setText(note.getContent());
            tvNoteUpdatedTime.setText(note.getUpdateTimeString());
        }
    }
}
