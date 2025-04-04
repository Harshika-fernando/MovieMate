package com.example.moviemateoriginal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ShowTimingsActivity extends AppCompatActivity {
    private TextView movieTitleTextView, selectedDateTextView;
    private ImageView moviePosterImageView;
    private ListView showtimeListView;
    private Button nextButton, backButton, pickDateButton;
    private String selectedShowtime;
    private String selectedDate;

    private String[] showtimes = {"10:00 AM - Hall 1", "1:00 PM - Hall 2", "4:00 PM - Hall 3", "7:00 PM - Hall 1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_timings);

        // Initialize UI elements
        movieTitleTextView = findViewById(R.id.movie_title_textview);
        moviePosterImageView = findViewById(R.id.movie_poster_imageview);
        selectedDateTextView = findViewById(R.id.tvSelectedDate);
        showtimeListView = findViewById(R.id.listViewShowtimes);
        nextButton = findViewById(R.id.btnNext);
        backButton = findViewById(R.id.btnBack);
        pickDateButton = findViewById(R.id.btnPickDate);

        // Get movie title and poster from intent and set them
        String movieTitle = getIntent().getStringExtra("MOVIE_TITLE");
        int moviePosterResId = getIntent().getIntExtra("MOVIE_POSTER", R.drawable.default_movie_poster); // Default poster if not passed

        if (movieTitle != null) {
            movieTitleTextView.setText(movieTitle);
        }

        // Set the movie poster
        moviePosterImageView.setImageResource(moviePosterResId);

        // Populate showtime list
        ArrayAdapter<String> showtimeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, showtimes);
        showtimeListView.setAdapter(showtimeAdapter);
        showtimeListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Handle showtime selection
        showtimeListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedShowtime = showtimes[position];
        });

        // Handle Date Picker button click
        pickDateButton.setOnClickListener(v -> {
            // Get current date to initialize DatePickerDialog
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            // Create DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ShowTimingsActivity.this,
                    (view1, year1, month1, dayOfMonth1) -> {
                        // Format the selected date as a string
                        selectedDate = dayOfMonth1 + "/" + (month1 + 1) + "/" + year1;
                        selectedDateTextView.setText("Selected Date: " + selectedDate); // Display selected date
                    },
                    year, month, dayOfMonth // Set the current date
            );
            datePickerDialog.show();
        });

        // Next button to proceed to seat selection
        nextButton.setOnClickListener(v -> {
            if (selectedShowtime != null && selectedDate != null) {
                Intent intent = new Intent(ShowTimingsActivity.this, SeatSelectionActivity.class);
                intent.putExtra("MOVIE_TITLE", movieTitle);
                intent.putExtra("SELECTED_SHOWTIME", selectedShowtime);
                intent.putExtra("SELECTED_DATE", selectedDate);
                startActivity(intent);
            } else {
                Toast.makeText(ShowTimingsActivity.this, "Please select a showtime and date.", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button to go back to movie details
        backButton.setOnClickListener(v -> finish());
    }
}
