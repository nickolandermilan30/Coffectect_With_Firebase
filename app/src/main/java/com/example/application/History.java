package com.example.application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {


    private List<SavedResult> savedResultsList = new ArrayList<>();
    private boolean isRecommendationClickable = true;
    private Button recommendationButton;
    Button cal, his;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Hide the action bar (app bar or title bar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        recommendationButton = findViewById(R.id.recommendationButton);
        Button clearResultsButton = findViewById(R.id.clearResultsButton);

        // Retrieve saved results from SavedResultsManager
        List<SavedResult> savedResults = SavedResultsManager.getSavedResultsList();

        // Initialize RecyclerView and set adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        SavedResultsAdapter adapter = new SavedResultsAdapter(savedResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Get recommendation
        String mostFrequentAndSevereDisease = SavedResultsManager.getMostFrequentDisease();

        // Configure recommendation button
        Button recommendationButton = findViewById(R.id.recommendationButton);

        // Check if there are at least 10 saved results to show the recommendation button
        if (SavedResultsManager.getSavedResultsList().size() < 10) {
            recommendationButton.setVisibility(View.GONE); // Hide the recommendation button
        }

        recommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecommendationClickable) { // Check if button is clickable
                    Intent intent = new Intent(History.this, RecommendationActivity.class);
                    intent.putExtra("diseaseName", mostFrequentAndSevereDisease);
                    startActivity(intent);
                }
            }
        });

        clearResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(History.this);
                builder.setTitle("Clear Saved Results");
                builder.setMessage("Are you sure you want to clear all saved results? This action cannot be undone.");
                builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isRecommendationClickable = false;
                        SavedResultsManager.clearSavedResults();

                        // Refresh the RecyclerView
                        savedResultsList.clear();
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        SavedResultsAdapter adapter = new SavedResultsAdapter(savedResultsList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(History.this));
                        recyclerView.setAdapter(adapter);

                        recommendationButton.setVisibility(View.GONE);
                        isRecommendationClickable = true;
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing, user cancelled the clear operation
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}