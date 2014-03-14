package com.example.vkexample.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public abstract class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "vkexample.db";
    private static final int DATABASE_VERSION = 1;

    protected final String TAG = getClass().getName();

    public DatabaseHelper(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
	try {
	    createTables(connectionSource);
	} catch (SQLException e) {
	    Log.e(TAG, "Could not create new table", e);
	}
    }

    protected void createTables(ConnectionSource connectionSource) throws SQLException {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion,
	    int newVersion) {
	try {
	    dropTables(connectionSource);
	    onCreate(sqLiteDatabase, connectionSource);
	} catch (SQLException e) {
	    Log.e(TAG, "Could not upgrade the tables", e);
	}
    }

    protected void dropTables(ConnectionSource connectionSource) throws SQLException {

    }

    public void clear() {
	try {
	    clearTables(connectionSource);
	} catch (Exception e) {
	    Log.e(TAG, "Could not upgrade the tables", e);
	}
    }

    protected void clearTables(AndroidConnectionSource connectionSource) throws SQLException {
    }
}
