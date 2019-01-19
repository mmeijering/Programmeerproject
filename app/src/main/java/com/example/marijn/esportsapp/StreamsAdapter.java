package com.example.marijn.esportsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StreamsAdapter extends ArrayAdapter<StreamsInformation> {

    public StreamsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<StreamsInformation> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override // Method that will be called every time a new list item (streamer) is to be displayed
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        // Get the index of the stream that we want to display
        StreamsInformation streamInfo = getItem(position);

        // If the convert view is null, inflate a new one
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stream_row, parent, false);
        }

        // Get the ID's of various TextViews and an ImageView
        TextView title = convertView.findViewById(R.id.titleView);
        TextView name = convertView.findViewById(R.id.nameView);
        TextView views = convertView.findViewById(R.id.viewersView);
        ImageView logo = convertView.findViewById(R.id.logoView);

        // Set the name, title and viewer count of the streamer
        title.setText(streamInfo.getTitle());
        name.setText(streamInfo.getName());
        views.setText(streamInfo.getViewers());

        // Load image from the internet into an image view using Picasso
        Picasso.get().load(streamInfo.getImageUrl()).into(logo);

        return convertView;
    }
}
