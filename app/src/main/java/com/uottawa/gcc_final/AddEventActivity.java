package com.uottawa.gcc_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {
    private Spinner eventTypeSpinner;
    private EditText eventDetailsEditText;
    private Spinner levelSpinner;
    private Button addEventButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        eventTypeSpinner = findViewById(R.id.eventTypeSpinner);
        eventDetailsEditText = findViewById(R.id.eventDetailsEditText);
        levelSpinner = findViewById(R.id.levelSpinner);
        addEventButton = findViewById(R.id.addEventButton);

        // Setup Spinners
        setupEventTypeSpinner();
        setupLevelSpinner();

        // Add event button click listener
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String eventType = eventTypeSpinner.getSelectedItem().toString();
                String eventDetails = eventDetailsEditText.getText().toString();
                String level = levelSpinner.getSelectedItem().toString();

                // Add event to the database
                long result = addEventToDatabase(eventType, eventDetails, level);

                if (result != -1) {
                    Toast.makeText(AddEventActivity.this, "Event added successfully", Toast.LENGTH_SHORT).show();

                    // After adding the event, navigate back to the WelcomeActivity
                    Intent intent = new Intent(AddEventActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddEventActivity.this, "Error adding event", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void setupEventTypeSpinner() {
        List<String> eventTypes = new ArrayList<>();
        // Add event types (modify as needed)
        eventTypes.add("Time Trial");
        eventTypes.add("Hill Climb");
        eventTypes.add("Road Stage Race");
        eventTypes.add("Other");
        // Add more event types...

        ArrayAdapter<String> eventTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventTypes);
        eventTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventTypeSpinner.setAdapter(eventTypeAdapter);
    }

    private void setupLevelSpinner() {
        List<String> levels = new ArrayList<>();
        // Add levels (modify as needed)
        levels.add("Beginner");
        levels.add("Intermediate");
        levels.add("Advanced");
        // Add more levels...

        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, levels);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(levelAdapter);
    }

    private long addEventToDatabase(String eventType, String eventDetails, String level) {
        dbHelper = new DatabaseHelper(this);
        long result = dbHelper.insertEvent(eventType, eventDetails, level);

        if (result != -1) {
            Log.d("AddEventActivity", "Event added successfully. Row ID: " + result);
        } else {
            Log.e("AddEventActivity", "Error adding event");
        }

        return result;
    }

    private int editEventInDatabase(String eventType, String eventDetails, String level) {
        dbHelper = new DatabaseHelper(this);
        return dbHelper.updateEvent(eventType, eventDetails, level);
    }

    private int deleteEventFromDatabase(String eventType) {
        dbHelper = new DatabaseHelper(this);
        return dbHelper.deleteEvent(eventType);
    }
}
