package com.rikkeisoft.mobile.databasedemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rikkeisoft.mobile.databasedemo.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HoangVuNam on 4/12/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * Database name
     */
    private static final String DB_NAME = "SQLDemo";

    /**
     * Table name
     */
    private static final String TABLE_NOTE = "notes";
    // Start -List table columns
    public static final String COLUMN_NOTE_ID = "id";
    public static final String COLUMN_NOTE_TITLE = "title";
    public static final String COLUMN_NOTE_CONTENT = "content";
    public static final String COLUMN_NOTE_UPDATED_TIME = "updated_time";
    // End - List table columns

    /**
     * Database version
     */
    private static final int DB_VERSION = 1;

    /**
     * Systax: Create database
     */
    private static final String CREATE_DB = "CREATE TABLE " + TABLE_NOTE + "("
            + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NOTE_TITLE + " TEXT,"
            + COLUMN_NOTE_CONTENT + " TEXT, "
            + COLUMN_NOTE_UPDATED_TIME + " LONG)";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // To be called when update database by change DB_VERSION value
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(db);
    }

    /**
     * Get all notes from table note
     * @return List all notes
     */
    public List<Note> getAllNotes() {
        List<Note> allNotes = new ArrayList<>();

        String getAllNoteQuery = "SELECT * FROM " + TABLE_NOTE;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(getAllNoteQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_NOTE_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_CONTENT)));
                note.setUpdatedTime(cursor.getLong(cursor.getColumnIndex(COLUMN_NOTE_UPDATED_TIME)));
                allNotes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allNotes;
    }

    public void insertNewNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getTitle()); // Contact Name
        values.put(COLUMN_NOTE_CONTENT, note.getContent()); // Contact Phone
        values.put(COLUMN_NOTE_UPDATED_TIME, note.getUpdatedTime()); // Contact Email
        // Inserting Row
        long id = db.insert(TABLE_NOTE, null, values);
        note.setID((int) id);
        db.close(); // Closing database connection
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String clause = COLUMN_NOTE_ID + " = ?";
            db.update(TABLE_NOTE, note.getContentValues(), clause, new String[] {String.valueOf(note.getID())});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String clause = COLUMN_NOTE_ID + " = ?";
            db.delete(TABLE_NOTE, clause, new String[] {String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }
}
