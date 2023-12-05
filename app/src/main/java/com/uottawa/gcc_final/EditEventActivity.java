package com.uottawa.gcc_final;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EditEventActivity extends AppCompatActivity {

    private ListView eventListView;
    private Button editButton;
    private List<String> eventList;
    private String selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        eventListView = findViewById(R.id.eventListView);
        editButton = findViewById(R.id.editButton);

        // Retrieve the list of existing events from the database
        eventList = new DatabaseHelper(this).getAllEvents();

        // Display the list of events in the ListView
        displayEventList();

        // Set item click listener for the ListView
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click, store selected event
                selectedEvent = eventList.get(position);

                // Enable the edit button
                editButton.setEnabled(true);
            }
        });

        // Edit Button click listener
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the logic to edit the selected event
                Toast.makeText(EditEventActivity.this, "Edit button clicked for event: " + selectedEvent, Toast.LENGTH_SHORT).show();
            }
        });

        // Initially disable the edit button
        editButton.setEnabled(false);
    }

    // Method to display the list of events in the ListView
    private void displayEventList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        eventListView.setAdapter(adapter);
    }
}
