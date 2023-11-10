package com.uottawa.gcc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

public class WelcomeActivity extends AppCompatActivity {
    private TextView welcomeTextView;
    private TextView roleTextView;
    private DatabaseHelper dbHelper;
    private Button addEventsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        welcomeTextView = findViewById(R.id.welcomeTextView);
        roleTextView = findViewById(R.id.roleTextView);
        addEventsButton = findViewById(R.id.addEventsButton);

        String username = getIntent().getStringExtra("username");

        // Get user info
        User user = getUserDetails(username);

        if (user != null) {
            welcomeTextView.setText("Welcome " + user.getUsername() + "!");
            roleTextView.setText("You are logged in as a \"" + user.getRole() + "\".");
        }

        if ("Administrator".equals(user.getRole())) {
            addEventsButton.setVisibility(View.VISIBLE);
            addEventsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to AddEventActivity
                    Intent intent = new Intent(WelcomeActivity.this, AddEventActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            addEventsButton.setVisibility(View.GONE);
        }


    }

    private User getUserDetails(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_USERNAME,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_ROLE
        };

        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, projection, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            // Reference for the following issue: "Cursor#getColumnIndex is annotated to return
            // value >= -1, and Cursor#getString requires an index >= 0. That means that the
            // argument could be a value outside of the allowed range (-1), which is why lint is
            // flagging it". I am following the fix shown in the reference.
            // https://issuetracker.google.com/issues/202193843?pli=1
            // Instead of int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            int columnIndexID = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int id = cursor.getInt(columnIndexID);
            int columnIndexEmail = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);
            String email = cursor.getString(columnIndexEmail);
            int columnIndexRole = cursor.getColumnIndex(DatabaseHelper.COLUMN_ROLE);
            String role = cursor.getString(columnIndexRole);
            cursor.close();
            return new User(id, username, email, role);
        }
        return null;
    }
}
