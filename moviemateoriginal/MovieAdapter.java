package com.example.moviemateoriginal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private String[] movieTitles;
    private int[] moviePosters; // Add your movie poster drawable IDs here

    // Constructor
    public MovieAdapter(Context context, String[] movieTitles, int[] moviePosters) {
        this.context = context;
        this.movieTitles = movieTitles;
        this.moviePosters = moviePosters;
    }

    @Override
    public int getCount() {
        return movieTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return movieTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.movie_item, parent, false);
        }

        ImageView poster = view.findViewById(R.id.movie_poster);
        TextView title = view.findViewById(R.id.movie_title);

        poster.setImageResource(moviePosters[position]);
        title.setText(movieTitles[position]);

        return view;
    }

    // Method to update the data in the adapter
    public void updateData(String[] newTitles, int[] newPosters) {
        this.movieTitles = newTitles;
        this.moviePosters = newPosters;
        notifyDataSetChanged();  // Notify the adapter to refresh the GridView
    }
}
