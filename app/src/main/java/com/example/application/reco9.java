package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class reco9 extends AppCompatActivity {

    private TextView titleTextView;
    private ScrollView paragraphScrollView;
    private TextView paragraphTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reco9);

        titleTextView = findViewById(R.id.titleTextView);
        paragraphScrollView = findViewById(R.id.paragraphScrollView);
        paragraphTextView = findViewById(R.id.paragraphTextView);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            // Retrieve the disease name passed from RecommendationActivity
            String diseaseName = intent.getStringExtra("diseaseName");

            // Set the most frequent disease as the title
            titleTextView.setText(diseaseName);

            // Use the disease name to fetch and display relevant recommendations
            if (paragraphTextView != null) {
                displayRecommendationsForDisease(diseaseName);
            } else {
                // Handle the case where paragraphTextView is null
                // Log an error, show a message, or take appropriate action
            }
        }
    }

    private void displayRecommendationsForDisease(String diseaseName) {
        String recommendation;

        switch (diseaseName) {
            case "Healthy Leaf":
                recommendation = "Recommendation for Healthy Leaf: Keep up with good plant care practices.";
                break;
            case "Phoma Mild":
                recommendation = "Recommendation for Phoma Mild: Implement mild treatment measures.";
                break;
            case "Phoma Moderate":
                recommendation = "Recommendation for Phoma Moderate: Implement moderate treatment measures.";
                break;
            case "Phoma Critical":
                recommendation = "Recommendation for Phoma Critical: Implement critical treatment measures.";
                break;
            case "Cercospora Mild":
                recommendation = "Recommendation for Cercospora Mild: Implement mild treatment measures.";
                break;
            case "Cercospora Moderate":
                recommendation = "Recommendation for Cercospora Moderate: Implement moderate treatment measures.";
                break;
            case "Cercospora Critical":
                recommendation = "Recommendation for Cercospora Critical: Implement critical treatment measures.";
                break;
            case "Leaf Miner Mild":
                recommendation = "Recommendation for Leaf Miner Mild: Implement mild treatment measures.";
                break;
            case "Leaf Miner Moderate":
                recommendation = "Recommendation for Leaf Miner Moderate: Implement moderate treatment measures.";
                break;
            case "Leaf Miner Critical":
                recommendation = "Recommendation for Leaf Miner Critical: Implement critical treatment measures.";
                break;
            case "Leaf Rust Mild":
                recommendation = "Recommendation for Leaf Rust Mild: Implement mild treatment measures.";
                break;
            case "Leaf Rust Moderate":
                recommendation = "Recommendation for Leaf Rust Moderate: Implement moderate treatment measures.";
                break;
            case "Leaf Rust Critical":
                recommendation = "Recommendation for Leaf Rust Critical: Implement critical treatment measures.";
                break;
            case "Sooty Mold Mild":
                recommendation = "Recommendation for Sooty Mold Mild: Implement mild treatment measures.";
                break;
            case "Sooty Mold Moderate":
                recommendation = "Recommendation for Sooty Mold Moderate: Implement moderate treatment measures.";
                break;
            case "Sooty Mold Critical":
                recommendation = "Recommendation for Sooty Mold Critical: Implement critical treatment measures.";
                break;
            default:
                recommendation = "No specific recommendation available for " + diseaseName;
                break;
        }

        // Set the recommendation text in your TextView
        paragraphTextView.setText(recommendation);
    }

}