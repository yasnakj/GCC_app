package com.uottawa.gcc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    // DB info
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cyclingClubDatabase";

    // Table name
    public static final String TABLE_USERS = "users";

    // Column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";

    // Create table query
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT NOT NULL,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT NOT NULL,"
            + COLUMN_ROLE + " TEXT NOT NULL" + ")";

    // As this is a subclass of the SQLiteOpenHelper, it follows the same properties which will
    // create a new database if there isnt one using the onCreate and if there is a database it will
    // just pass that through
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);

        // Insert initial data
        insertInitialData(db);
    }

    // Method to insert initial data
    private void insertInitialData(SQLiteDatabase db) {
        // Administrator account
        ContentValues adminValues = new ContentValues();
        adminValues.put(COLUMN_USERNAME, "admin");
        adminValues.put(COLUMN_EMAIL, "admin@example.com");
        adminValues.put(COLUMN_PASSWORD, "adminpass");
        adminValues.put(COLUMN_ROLE, "administrator");
        db.insert(TABLE_USERS, null, adminValues);

        // Regular user/participant account
        ContentValues userParticipantValues = new ContentValues();
        userParticipantValues.put(COLUMN_USERNAME, "userparticipant");
        userParticipantValues.put(COLUMN_EMAIL, "userparticipant@example.com");
        userParticipantValues.put(COLUMN_PASSWORD, "userparticipantpass");
        userParticipantValues.put(COLUMN_ROLE, "participant");
        db.insert(TABLE_USERS, null, userParticipantValues);

        // Regular user/organizer account
        ContentValues userOrganizerValues = new ContentValues();
        userOrganizerValues.put(COLUMN_USERNAME, "userorganizer");
        userOrganizerValues.put(COLUMN_EMAIL, "userorganizer@example.com");
        userOrganizerValues.put(COLUMN_PASSWORD, "userorganizerpass");
        userOrganizerValues.put(COLUMN_ROLE, "organizer");
        db.insert(TABLE_USERS, null, userOrganizerValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
