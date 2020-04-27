package com.vipromos.pursuit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pursuits")
public class Pursuit {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "text")
    private String mText = "";

    @ColumnInfo(name = "updated")
    private long mUpdateTime;

    @ColumnInfo(name="why")
    private String mWhy = "";

    @ColumnInfo(name="goal")
    private String mGoal = "";

    @ColumnInfo(name="priority")
    private int mPriority;

    @ColumnInfo(name="successcountshort")
    private int mSuccessCountShort = 0;

    @ColumnInfo(name="successcountmed")
    private int mSuccessCountMed = 0;

    @ColumnInfo(name="successcountlong")
    private int mSuccessCountLong = 0;

    @ColumnInfo(name="action")
    private String mAction = "";

    @ColumnInfo(name="created")
    private boolean mCreated = false;

    @ColumnInfo(name="completed")
    public int mCompleted = 0;






    public Pursuit() {
        mUpdateTime = System.currentTimeMillis();
    }

    public Pursuit(String text) {
        mText = text;
        mUpdateTime = System.currentTimeMillis();
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public long getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(long updateTime) {
        mUpdateTime = updateTime;
    }

    public void setGoal(String inputGoal) {
        mGoal = inputGoal;
    }
    public String getGoal() {
        return mGoal;
    }
    public void setWhy(String inputWhy) {
        mWhy = inputWhy;
    }
    public String getWhy() {
        return mWhy;
    }
    public void setPriority(int inputPriority) {
        mPriority = inputPriority;
    }
    public int getPriority() {
        return mPriority;
    }
    public void setAction(String inputAction) {
        mAction = inputAction;
    }
    public String getAction() {
        return mAction;
    }
    public void setCreated(boolean inputCreated) { mCreated = inputCreated; }
    public boolean getCreated() {
        return mCreated;
    }
    public void setSuccessCountShort(int inputCount) {
        mSuccessCountShort = inputCount;
    }
    public int getSuccessCountShort() {
        return mSuccessCountShort;
    }
    public void setSuccessCountMed(int inputCount) {
        mSuccessCountMed = inputCount;
    }
    public int getSuccessCountMed() {
        return mSuccessCountMed;
    }
    public void setSuccessCountLong(int inputCount) {
        mSuccessCountLong = inputCount;
    }
    public int getSuccessCountLong() {
        return mSuccessCountLong;
    }

    public void setCompleted(int inputCompleted) { mCompleted = inputCompleted; }
    public int getCompleted() {
        return mCompleted;
    }


}

