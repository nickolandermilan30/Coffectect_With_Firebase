package com.example.application;

import static com.example.application.SavedResultsManager.getHistory7List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class rec7 extends AppCompatActivity {

    private Map<String, Map<Integer, String>> recommendationsMap = new HashMap<>();
    private RatingBar ratingBar;
    private TextView ratingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec7);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeRecommendations();

        TextView diseaseTextView = findViewById(R.id.diseaseTextView);
        TextView severityTextView = findViewById(R.id.severityTextView);
        TextView recommendationTextView = findViewById(R.id.recommendationTextView);
        ImageView mostFrequentImageView = findViewById(R.id.mostFrequentImageView);

        Intent intent = getIntent();
        if (intent != null) {
            String diseaseName = intent.getStringExtra("diseaseName");
            int severityLevel = intent.getIntExtra("severityLevel", 0);

            String recommendation = getRecommendation(diseaseName, severityLevel);
            diseaseTextView.setText("Disease: " + diseaseName);
            severityTextView.setText("Severity: " + severityLevel);
            recommendationTextView.setText(recommendation);

            Bitmap mostFrequentImage = SavedResultsManager.getImageForMostFrequentDiseaseHistory7();
            if (mostFrequentImage != null) {
                mostFrequentImageView.setImageBitmap(mostFrequentImage);
            }
        }

        ImageButton legButton = findViewById(R.id.leg);
        legButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomAlertDialog();
            }
        });

        Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView mostFrequentTextView = findViewById(R.id.mostFrequentTextView);
        String mostFrequentHistory7Disease = SavedResultsManager.getMostFrequentDiseaseHistory7();
        if (mostFrequentHistory7Disease != null) {
            mostFrequentTextView.setText("Most Frequent Disease in History7: " + mostFrequentHistory7Disease);
        }
    }

    private void showCustomAlertDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_layout, null);
        ImageView imageView = dialogView.findViewById(R.id.customAlertDialogImageView);
        TextView textView = dialogView.findViewById(R.id.customAlertDialogTextView);
        textView.setText("This is The meaning of 1,2,3 on severity");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(dialogView);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void initializeRecommendations() {
        // Recommendations Map initialization...
    }

    private String getRecommendation(String diseaseName, int severityLevel) {
        Map<Integer, String> severityRecommendations = recommendationsMap.get(diseaseName);
        if (severityRecommendations != null) {
            String recommendation = severityRecommendations.get(severityLevel);
            if (recommendation != null) {
                return recommendation;
            }
        }
        return "No Recommendation Available";
    }

    public static Bitmap getImageForMostFrequentDiseaseHistory7() {
        List<SavedResult> history7List = getHistory7List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history7List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        SavedResult mostFrequentResult = null;

        for (SavedResult result : history7List) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                mostFrequentResult = result;
                break;
            }
        }

        if (mostFrequentResult != null) {
            return mostFrequentResult.getImageBitmap();
        }

        return null;
    }

    public static String getMostFrequentDiseaseHistory7() {
        List<SavedResult> history7List = getHistory7List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history7List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }

}
