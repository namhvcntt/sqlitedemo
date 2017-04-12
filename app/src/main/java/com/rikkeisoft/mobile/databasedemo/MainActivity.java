package com.rikkeisoft.mobile.databasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rikkeisoft.mobile.databasedemo.adapters.NoteAdapter;
import com.rikkeisoft.mobile.databasedemo.callbacks.ListNoteInteractiveListener;
import com.rikkeisoft.mobile.databasedemo.callbacks.NoteUpdateListener;
import com.rikkeisoft.mobile.databasedemo.customviews.ListNoteItemDecoration;
import com.rikkeisoft.mobile.databasedemo.database.DatabaseHelper;
import com.rikkeisoft.mobile.databasedemo.dialog.DialogEditNoteFragment;
import com.rikkeisoft.mobile.databasedemo.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ListNoteInteractiveListener, NoteUpdateListener {

    private List<Note> mNotes;

    // Views
    private RecyclerView mRcListNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize data
        mNotes = new ArrayList<>();

        // Initialize views
        // FAB add note
        findViewById(R.id.fabAddNote).setOnClickListener(this);

        mRcListNote = (RecyclerView) findViewById(R.id.rcListNotes);
        NoteAdapter adapter = new NoteAdapter(mNotes, this);
        mRcListNote.setAdapter(adapter);
//        mRcListNote.addItemDecoration(new ListNoteItemDecoration(this, ListNoteItemDecoration.VERTICAL_LIST));

        // Load data
        loadNote();

    }

    public void loadNote() {
        List<Note> noteListTemp = new DatabaseHelper(this).getAllNotes();
        if (null != noteListTemp && !noteListTemp.isEmpty()) {
            mNotes.addAll(noteListTemp);

            // Update list note
            mRcListNote.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddNote:
                DialogEditNoteFragment dialogFragment = DialogEditNoteFragment.newDialogNewNoteInstance();
                dialogFragment.setNoteUpdateListener(this);
                dialogFragment.show(getFragmentManager(), DialogEditNoteFragment.class.getSimpleName());
                break;
        }
    }

    public void onUserSelectDeleteNote(Note note) {
        // Update database
        new DatabaseHelper(this).deleteNote(note.getID());
        mNotes.remove(note);
        mRcListNote.getAdapter().notifyDataSetChanged();
    }

    public void onUserSelectEditNote(Note note) {
        // To be called when user select edit
        DialogEditNoteFragment dialogFragment = DialogEditNoteFragment.newDialogEditNoteInstance(note);
        dialogFragment.setNoteUpdateListener(this);
        dialogFragment.show(getFragmentManager(), DialogEditNoteFragment.class.getSimpleName());
    }

    @Override
    public void onCommitChange(Note note) {
        Note editedNote = findNoteInList(note.getID());
        editedNote.copyData(note);

        // Update database
        new DatabaseHelper(this).updateNote(note);
        // Update ui
        mRcListNote.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCommitNew(Note note) {
        // Insert note to db
        new DatabaseHelper(this).insertNewNote(note);
        // Add to top of list note
        mNotes.add(0, note);

        // Notify data
        mRcListNote.getAdapter().notifyDataSetChanged();
    }

    public Note findNoteInList(int id) {
        if (null != mNotes && !mNotes.isEmpty()) {
            for (int i = 0; i < mNotes.size(); i++) {
                Note note = mNotes.get(i);
                if (id == note.getID()) {
                    return note;
                }
            }
        }
        return null;
    }
}
