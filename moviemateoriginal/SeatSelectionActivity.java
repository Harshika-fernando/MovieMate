package com.example.moviemateoriginal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SeatSelectionActivity extends AppCompatActivity {
    private TextView movieShowtimeInfo, selectedSeatsSummary;
    private GridView seatGridView;
    private Button btnProceed, btnBack;
    private ArrayList<String> selectedSeats = new ArrayList<>();

    private String[] seats = {
            "A1", "A2", "A3", "A4", "A5",
            "B1", "B2", "B3", "B4", "B5",
            "C1", "C2", "C3", "C4", "C5",
            "D1", "D2", "D3", "D4", "D5"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        // Initialize UI Elements
        movieShowtimeInfo = findViewById(R.id.movie_showtime_info);
        selectedSeatsSummary = findViewById(R.id.selected_seats_summary);
        seatGridView = findViewById(R.id.seat_grid_view);
        btnProceed = findViewById(R.id.btnProceed);
        btnBack = findViewById(R.id.btnBack);

        // Get Movie Title and Showtime from Intent
        Intent intent = getIntent();
        String movieTitle = intent.getStringExtra("MOVIE_TITLE");
        String showtime = intent.getStringExtra("SELECTED_SHOWTIME");

        movieShowtimeInfo.setText(movieTitle + " - " + showtime);

        // Set up GridView with Seats
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, seats);
        seatGridView.setAdapter(adapter);
        seatGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);

        // Handle Seat Selection
        seatGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSeat = seats[position];

                if (selectedSeats.contains(selectedSeat)) {
                    selectedSeats.remove(selectedSeat);
                } else {
                    selectedSeats.add(selectedSeat);
                }

                selectedSeatsSummary.setText("Selected Seats: " + selectedSeats.toString());
            }
        });

        // Proceed Button Click (Move to Ticket Confirmation Page)
        btnProceed.setOnClickListener(v -> {
            if (!selectedSeats.isEmpty()) {
                Intent nextIntent = new Intent(SeatSelectionActivity.this, TicketConfirmationActivity.class);
                nextIntent.putExtra("MOVIE_TITLE", movieTitle);
                nextIntent.putExtra("SELECTED_SHOWTIME", showtime);
                nextIntent.putStringArrayListExtra("SELECTED_SEATS", selectedSeats);
                startActivity(nextIntent);
            } else {
                Toast.makeText(SeatSelectionActivity.this, "Please select at least one seat!", Toast.LENGTH_SHORT).show();
            }
        });

        // Back Button Click (Go back to Show Timings Page)
        btnBack.setOnClickListener(v -> finish());
    }
}
