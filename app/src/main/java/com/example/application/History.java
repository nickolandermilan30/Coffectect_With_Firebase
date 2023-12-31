package com.example.application;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private List<SavedResult> savedResultsList = new ArrayList<>();
    private boolean isRecommendationClickable = true;
    private Button recommendationButton;
    Button clearResultsButton;
    TextView savedResultsCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Hide the action bar (app bar or title bar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        savedResultsCountTextView = findViewById(R.id.savedResultsCount);

        recommendationButton = findViewById(R.id.recommendationButton);
        clearResultsButton = findViewById(R.id.clearResultsButton);

        // Retrieve saved results from SavedResultsManager
        List<SavedResult> savedResults = SavedResultsManager.getSavedResultsList();

        // Update the saved results count TextView
        updateSavedResultsCount(savedResults.size());

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

                        // Update the saved results count TextView to 0
                        updateSavedResultsCount(0);
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

    private void updateSavedResultsCount(int count) {
        savedResultsCountTextView.setText("Saved Results: " + count);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
