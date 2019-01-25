package com.example.marijn.esportsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static android.content.Context.MODE_PRIVATE;

public class FavouritesFragment extends Fragment implements FavouritesRequest.Callback {

    private View rootView;
    private List<String> favouriteStreamers = new ArrayList<>();
    private ArrayList<FavouritesInformation> favouriteInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_favourites, container, false);

        SharedPreferences prefA = getContext().getSharedPreferences("favourite", MODE_PRIVATE);

        // With help from: https://stackoverflow.com/questions/35536415/
        Map<String, ?> allEntries = prefA .getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue().equals(true))
                // Make api request with the name
                favouriteStreamers.add(entry.getKey());
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StringJoiner joiner = new StringJoiner(",");

        for (String streamer : favouriteStreamers) {
            joiner.add(streamer);
        }

        String joinedString = joiner.toString();

        FavouritesRequest favouriteStreamRequest = new FavouritesRequest(getActivity());
        favouriteStreamRequest.getFavourite(FavouritesFragment.this, joinedString);

        // Instantiate an on list item click listener
        ListView listView = rootView.findViewById(R.id.favouriteList);
        listView.setOnItemClickListener(new FavouritesFragment.ItemClickListener());
    }

    @Override // Method that handles a successful call to the API
    public void gotFavourite(ArrayList<FavouritesInformation> favouriteInf) {

        favouriteInfo = favouriteInf;

        // If statement to make sure the app doesn't crash when a fragment is clicked multiple times
        // With help from: https://stackoverflow.com/questions/39532507/
        if (getActivity() != null) {

            // Instantiate the adapter
            FavouritesAdapter favouriteAdapter = new FavouritesAdapter(getActivity(), R.layout.favourite_row, favouriteInfo);

            // Get list view ID and attach the adapter to it
            ListView favouriteList = rootView.findViewById(R.id.favouriteList);
            favouriteList.setAdapter(favouriteAdapter);
        }
    }

    @Override // Method that handles an unsuccessful to the the API
    public void gotFavouriteError(String message) {
        // Toast the error message to the screen
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        Log.d("error", message);
    }

    // Create an on menu item clicked listener
    private class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Get the MenuItem object of the clicked item in the list view
            FavouritesInformation clickedFavouriteStream = favouriteInfo.get(position);

            // Put menu item information into the bundle
            String urlToStream = clickedFavouriteStream.getTwitchUrl();

            // https://stackoverflow.com/questions/2201917/
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToStream));
            startActivity(browserIntent);
        }
    }
}
