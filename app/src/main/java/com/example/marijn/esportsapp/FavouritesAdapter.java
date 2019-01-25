package com.example.marijn.esportsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FavouritesAdapter extends ArrayAdapter<FavouritesInformation> {

    public FavouritesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FavouritesInformation> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override // Method that will be called every time a new list item (streamer) is to be displayed
    public View getView(final int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        // Get the index of the fav streamer that we want to display
        FavouritesInformation favouriteInfo = getItem(position);

        // If the convert view is null, inflate a new one
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.favourite_row, parent, false);
        }

        // Get various ID's
        TextView title = convertView.findViewById(R.id.titleView);
        TextView game = convertView.findViewById(R.id.gameView);
        TextView name = convertView.findViewById(R.id.nameView);
        TextView views = convertView.findViewById(R.id.viewersView);
        ImageView logo = convertView.findViewById(R.id.logoView);
        ToggleButton favButton = convertView.findViewById(R.id.favouriteButton);

        // Set the name, title, game and viewer count of the streamer
        title.setText(favouriteInfo.getTitle());
        game.setText(favouriteInfo.getGame());
        name.setText(favouriteInfo.getName());
        views.setText(favouriteInfo.getViewers());

        // Load the streamer's logo into an image view using Picasso
        Picasso.get().load(favouriteInfo.getImageUrl()).resize(250, 250).onlyScaleDown().into(logo);

        // Get a previously stored favourite boolean
        SharedPreferences prefsFav = getContext().getSharedPreferences("favourite", MODE_PRIVATE);
        Boolean isFav = prefsFav.getBoolean(favouriteInfo.getName(), false);

        // Set the previously stored favourite state
        if (isFav) { // If this person was a favourite
            favButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.yellow_star));

        } else { // If this person was NOT a favourite
            favButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.grey_star));
        }

        favButton.setTag(favouriteInfo.getName());

        // Set on check change listener (toggle button)
        favButton.setOnCheckedChangeListener(new OnCheckedChangeListener());

        return convertView;
    }

    // Create an on favourite button click listener
    private class OnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean isChecked){

        // https://stackoverflow.com/questions/16821419/
        ToggleButton favButton = (ToggleButton) buttonView;

        if (isChecked) { // If favourited
            favButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.yellow_star));
        } else { // If unfavourited
            favButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.grey_star));
        }

        String streamerName = (String) buttonView.getTag();

        // Edit the old favourite Boolean and store the new value
        SharedPreferences.Editor editor = getContext().getSharedPreferences("favourite", MODE_PRIVATE).edit();
        editor.putBoolean(streamerName, isChecked);
        editor.apply();
        }
    }
}




