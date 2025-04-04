package com.example.moviemateoriginal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailsActivity extends AppCompatActivity {
    private ImageView moviePoster;
    private TextView movieTitle, movieGenre, movieRating, movieDescription;
    private Button showtimesButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Initialize UI elements
        moviePoster = findViewById(R.id.movie_poster);
        movieTitle = findViewById(R.id.movie_title);
        movieGenre = findViewById(R.id.movie_genre);
        movieRating = findViewById(R.id.movie_rating);
        movieDescription = findViewById(R.id.movie_description);
        showtimesButton = findViewById(R.id.showtimes_button);
        backButton = findViewById(R.id.back_button);

        // Get movie data from Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("MOVIE_TITLE");
        String genre = intent.getStringExtra("MOVIE_GENRE");
        String rating = intent.getStringExtra("MOVIE_RATING");
        String description = intent.getStringExtra("MOVIE_DESCRIPTION");
        int poster = intent.getIntExtra("MOVIE_POSTER", R.drawable.default_movie_poster);

        // Set the data in UI elements
        moviePoster.setImageResource(poster);
        movieTitle.setText(title);
        movieGenre.setText("Genre: " + genre);
        movieRating.setText("Rating: " + rating);
        movieDescription.setText(description);

        // Showtimes button (navigate to showtimes screen)

        showtimesButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(MovieDetailsActivity.this, ShowTimingsActivity.class);
            String title1 = movieTitle.getText().toString(); // Get the movie title
            intent1.putExtra("MOVIE_TITLE", title); // Pass the movie title to ShowTimingsActivity
            startActivity(intent1);
        });


        // Back button (go back to Movie Listings)
        backButton.setOnClickListener(v -> finish());
    }
}
