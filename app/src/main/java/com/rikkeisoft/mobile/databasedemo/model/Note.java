package com.rikkeisoft.mobile.databasedemo.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.rikkeisoft.mobile.databasedemo.database.DatabaseHelper;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by HoangVuNam on 4/12/17.
 */

public class Note implements Parcelable {
    public int mID = -1;
    public String mTitle;
    public String mContent;
    public long mUpdatedTime;

    public Note() {
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public long getUpdatedTime() {
        return mUpdatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        mUpdatedTime = updatedTime;
    }

    public String getUpdateTimeString() {
        DateFormat simpleDateFormat = SimpleDateFormat.getDateTimeInstance();
        Date date = new Date(getUpdatedTime());
        return simpleDateFormat.format(date).toString();
    }


    public void copyData(Note note) {
        mTitle = note.getTitle();
        mContent = note.getContent();
        mUpdatedTime = note.getUpdatedTime();
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_NOTE_TITLE, mTitle);
        contentValues.put(DatabaseHelper.COLUMN_NOTE_CONTENT, mContent);
        contentValues.put(DatabaseHelper.COLUMN_NOTE_UPDATED_TIME, mUpdatedTime);
        return contentValues;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mID);
        dest.writeString(this.mTitle);
        dest.writeString(this.mContent);
        dest.writeLong(this.mUpdatedTime);
    }

    protected Note(Parcel in) {
        this.mID = in.readInt();
        this.mTitle = in.readString();
        this.mContent = in.readString();
        this.mUpdatedTime = in.readLong();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
