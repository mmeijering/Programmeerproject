package com.example.marijn.esportsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // On button click, show Categories
    public void onStartClicked(View view) {
        startActivity(new Intent(this, CategoriesActivity.class));
    }
}
