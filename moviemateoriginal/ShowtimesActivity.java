package com.example.moviemateoriginal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShowtimesActivity extends AppCompatActivity {

    private TextView tvSelectedDate;
    private Button btnPickDate, btnBack, btnNext;
    private ListView listViewShowtimes;
    private List<String> showtimes;
    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtimes);

        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        listViewShowtimes = findViewById(R.id.listViewShowtimes);

        // Default Showtimes List
        showtimes = new ArrayList<>();
        showtimes.add("10:00 AM - Screen 1");
        showtimes.add("12:30 PM - Screen 2");
        showtimes.add("3:00 PM - Screen 3");
        showtimes.add("6:00 PM - Screen 1");
        showtimes.add("8:30 PM - Screen 2");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showtimes);
        listViewShowtimes.setAdapter(adapter);

        // Date Picker Dialog
        btnPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ShowtimesActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        tvSelectedDate.setText("Selected Date: " + selectedDate);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Handle Time Slot Click
        listViewShowtimes.setOnItemClickListener((parent, view, position, id) -> {
            String selectedShowtime = showtimes.get(position);
            Toast.makeText(ShowtimesActivity.this, "Selected: " + selectedShowtime, Toast.LENGTH_SHORT).show();

            // Save selected showtime (can be passed to next screen)
            Intent intent = new Intent(ShowtimesActivity.this, SeatSelectionActivity.class);
            intent.putExtra("SHOWTIME", selectedShowtime);
            intent.putExtra("DATE", selectedDate);
            startActivity(intent);
        });

        // Back Button Action
        btnBack.setOnClickListener(v -> finish());

        // Next Button Action
        btnNext.setOnClickListener(v -> {
            if (selectedDate.isEmpty()) {
                Toast.makeText(ShowtimesActivity.this, "Please select a date first!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(ShowtimesActivity.this, SeatSelectionActivity.class);
                intent.putExtra("DATE", selectedDate);
                startActivity(intent);
            }
        });
    }
}
