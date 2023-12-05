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

public class DeleteEventActivity extends AppCompatActivity {

    private ListView eventListView;
    private Button deleteButton;
    private List<String> eventList;
    private String selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_event);

        eventListView = findViewById(R.id.eventListView);
        deleteButton = findViewById(R.id.deleteButton);

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

                // Enable the delete button
                deleteButton.setEnabled(true);
            }
        });

        // Delete Button click listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the logic to delete the selected event
                Toast.makeText(DeleteEventActivity.this, "Delete button clicked for event: " + selectedEvent, Toast.LENGTH_SHORT).show();
            }
        });

        // Initially disable the delete button
        deleteButton.setEnabled(false);
    }

    // Method to display the list of events in the ListView
    private void displayEventList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        eventListView.setAdapter(adapter);
    }
}

