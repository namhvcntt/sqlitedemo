package com.rikkeisoft.mobile.databasedemo.callbacks;

import com.rikkeisoft.mobile.databasedemo.model.Note;

/**
 * Created by HoangVuNam on 4/12/17.
 */

public interface NoteUpdateListener {
    void onCommitChange(Note note);
    void onCommitNew(Note note);
}
