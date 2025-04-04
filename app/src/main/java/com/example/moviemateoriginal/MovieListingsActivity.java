package com.example.moviemateoriginal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class MovieListingsActivity extends AppCompatActivity {
    private GridView gridView;
    private MovieAdapter movieAdapter;
    private EditText searchBar;
    private Spinner filterSpinner;

    // Movie data
    private String[] movieTitles = {"Wish", "Lost Tiger", "Strange World", "Inside Out", "Over The Moon"};
    private int[] moviePosters = {
            R.drawable.movie1_poster,
            R.drawable.movie2_poster,
            R.drawable.movie3_poster,
            R.drawable.movie4_poster,
            R.drawable.movie5_poster
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_listings);

        // Initialize the search bar and filter spinner
        searchBar = findViewById(R.id.search_bar);
        filterSpinner = findViewById(R.id.filter_spinner);

        // Set up the OnEditorActionListener for the search bar (when the user taps the search icon or presses enter)
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchBar.getText().toString().trim();
                if (!query.isEmpty()) {
                    searchForMovie(query);
                } else {
                    Toast.makeText(MovieListingsActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
                }
                return true;  // Return true to indicate the event was handled
            }
            return false; // Return false if it's not the search action
        });

        // Set up the GridView with the MovieAdapter
        gridView = findViewById(R.id.movie_grid);
        movieAdapter = new MovieAdapter(this, movieTitles, moviePosters);
        gridView.setAdapter(movieAdapter);

        // Set up filter options for the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.movie_filters, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        // Bottom navigation setup
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                startActivity(new Intent(MovieListingsActivity.this, ProfileActivity.class));
                return true;
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(MovieListingsActivity.this, SettingsActivity.class));
                return true;
            } else if (id == R.id.nav_history) {
                startActivity(new Intent(MovieListingsActivity.this, HistoryActivity.class));
                return true;
            }
            return false;
        });

        // Grid item click listener for movie details
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MovieListingsActivity.this, MovieDetailsActivity.class);
            intent.putExtra("MOVIE_TITLE", movieTitles[position]);
            intent.putExtra("MOVIE_GENRE", "Genre of " + movieTitles[position]); // Replace with actual genre
            intent.putExtra("MOVIE_RATING", "4.5"); // Replace with actual rating
            intent.putExtra("MOVIE_DESCRIPTION", "This is a brief description of " + movieTitles[position]);
            intent.putExtra("MOVIE_POSTER", moviePosters[position]);
            startActivity(intent);
        });
    }

    private void searchForMovie(String query) {
        // Create lists to hold the filtered movie titles and posters
        List<String> filteredTitleList = new ArrayList<>();
        List<Integer> filteredPosterList = new ArrayList<>();

        // Loop through movie titles and check if they contain the search query
        for (int i = 0; i < movieTitles.length; i++) {
            if (movieTitles[i].toLowerCase().contains(query.toLowerCase())) {
                filteredTitleList.add(movieTitles[i]);
                filteredPosterList.add(moviePosters[i]);
            }
        }

        // If no results are found, display a toast message
        if (filteredTitleList.isEmpty()) {
            Toast.makeText(MovieListingsActivity.this, "No movies found", Toast.LENGTH_SHORT).show();
        } else {
            // Update the adapter with the filtered data
            String[] filteredTitles = filteredTitleList.toArray(new String[0]);
            int[] filteredPosters = new int[filteredPosterList.size()];
            for (int i = 0; i < filteredPosterList.size(); i++) {
                filteredPosters[i] = filteredPosterList.get(i);
            }

            // Create a new adapter with the filtered movie data
            movieAdapter = new MovieAdapter(MovieListingsActivity.this, filteredTitles, filteredPosters);
            gridView.setAdapter(movieAdapter);
        }
    }
}
