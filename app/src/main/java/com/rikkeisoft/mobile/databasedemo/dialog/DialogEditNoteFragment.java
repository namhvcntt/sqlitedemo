package com.rikkeisoft.mobile.databasedemo.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.rikkeisoft.mobile.databasedemo.R;
import com.rikkeisoft.mobile.databasedemo.callbacks.NoteUpdateListener;
import com.rikkeisoft.mobile.databasedemo.model.Note;

/**
 * Dialog create and edit note
 * Created by HoangVuNam on 4/12/17.
 */

public class DialogEditNoteFragment extends DialogFragment {

    public static final String ARG_NOTE = "note";

    private NoteUpdateListener mNoteUpdateListener;

    private boolean isCreateNewNote = true;
    private Note mCurrentNote;

    // Views
    private EditText mEdtTitle;
    private EditText mEdtContent;

    public static DialogEditNoteFragment newDialogNewNoteInstance() {
        return new DialogEditNoteFragment();
    }

    public static DialogEditNoteFragment newDialogEditNoteInstance(Note note) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        DialogEditNoteFragment dialogEditNoteFragment = new DialogEditNoteFragment();
        dialogEditNoteFragment.setArguments(bundle);
        return dialogEditNoteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            mCurrentNote = bundle.getParcelable(ARG_NOTE);
            if (null != mCurrentNote) {
                isCreateNewNote = false;
                return;
            }
        }
        mCurrentNote = new Note();
        isCreateNewNote = true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String dialogTitle = isCreateNewNote? "New Note" : "Update Note";
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setTitle(dialogTitle)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // do something...
                                if (null != mNoteUpdateListener) {
                                    getChangeData();
                                    if (isCreateNewNote) {
                                        mNoteUpdateListener.onCommitNew(mCurrentNote);
                                    } else {
                                        mNoteUpdateListener.onCommitChange(mCurrentNote);
                                    }
                                }
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );

        LayoutInflater i = getActivity().getLayoutInflater();

        View v = i.inflate(R.layout.fragment_edit_note, null);
        bindNoteData(v, mCurrentNote);

        b.setView(v);
        b.setCancelable(false);
        return b.create();
    }

    private void bindNoteData(View rootView, Note note) {
        mEdtTitle = (EditText) rootView.findViewById(R.id.edtNoteTitle);
        mEdtTitle.setText(note.getTitle());
        mEdtContent = (EditText) rootView.findViewById(R.id.edtNoteContent);
        mEdtContent.setText(note.getContent());
    }

    private void getChangeData() {
        String title = mEdtTitle.getText().toString();
        String content = mEdtContent.getText().toString();
        mCurrentNote.setTitle(title);
        mCurrentNote.setContent(content);
        mCurrentNote.setUpdatedTime(System.currentTimeMillis());
    }

    public void setNoteUpdateListener(NoteUpdateListener noteUpdateListener) {
        mNoteUpdateListener = noteUpdateListener;
    }
}
