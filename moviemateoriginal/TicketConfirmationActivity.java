package com.example.moviemateoriginal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TicketConfirmationActivity extends AppCompatActivity {
    private TextView movieTitleTextView, showtimeTextView, selectedSeatsTextView, totalPriceTextView;
    private ImageView moviePosterImageView;
    private Button btnConfirmBooking, btnBack;

    private ArrayList<String> selectedSeats;
    private String movieTitle, showtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_confirmation);

        // Initialize UI Elements
        movieTitleTextView = findViewById(R.id.movie_title_textview);
        moviePosterImageView = findViewById(R.id.movie_poster);
        showtimeTextView = findViewById(R.id.showtime_textview);
        selectedSeatsTextView = findViewById(R.id.selected_seats_textview);
        totalPriceTextView = findViewById(R.id.total_price_textview);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
        btnBack = findViewById(R.id.btnBack);

        // Get data from the intent
        Intent intent = getIntent();
        movieTitle = intent.getStringExtra("MOVIE_TITLE");
        showtime = intent.getStringExtra("SELECTED_SHOWTIME");
        selectedSeats = intent.getStringArrayListExtra("SELECTED_SEATS");

        // Set data to views
        movieTitleTextView.setText(movieTitle);
        showtimeTextView.setText("Showtime: " + showtime);
        selectedSeatsTextView.setText("Selected Seats: " + selectedSeats.toString());

        // Assuming each seat costs $10 (adjust as needed)
        int totalCost = selectedSeats.size() * 10;
        totalPriceTextView.setText("Total Price: $" + totalCost);

        // For now, set a default movie poster
        moviePosterImageView.setImageResource(R.drawable.ic_movie_poster);  // Replace with your movie poster image

        // Proceed with Booking
        btnConfirmBooking.setOnClickListener(v -> {
            Toast.makeText(TicketConfirmationActivity.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
            // Optionally, redirect to a payment gateway or other final confirmation
        });

        // Back Button to go back to Seat Selection Page
        btnBack.setOnClickListener(v -> finish());
    }
}
