package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Calendar extends AppCompatActivity {


    private PieChart pieChart;
    private RecyclerView recyclerView;
    private Button clearButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Hide the action bar (app bar or title bar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        pieChart = findViewById(R.id.pieChart);
        recyclerView = findViewById(R.id.recyclerView);
        clearButton = findViewById(R.id.clearButton);

        // Retrieve data from RecommendationActivity
        Intent intent = getIntent();
        if (intent != null) {
            String diseaseName = intent.getStringExtra("diseaseName");
            int severityLevel = intent.getIntExtra("severityLevel", 0);

            // Update the PieChart based on diseaseName and severityLevel
            updatePieChart(diseaseName, severityLevel);
        }
        
        
        // Initialize PieChart
        initializePieChart();

        // Initialize RecyclerView
        initializeRecyclerView();

        // Add back button functionality
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRecyclerView();
            }
        });
    }

    private void updatePieChart(String diseaseName, int severityLevel) {
        // Clear existing entries
        DiseaseData.clearPieEntries();

        // Add new entries based on the disease name and severity level
        float percentage = 100f; // You can set the percentage based on your logic
        PieEntry entry = new PieEntry(percentage, diseaseName);
        DiseaseData.addPieEntry(entry);

        // Refresh the PieChart
        initializePieChart();
    }



    public void onClearButtonClick(View view) {
        clearRecyclerView();
    }

    private void clearRecyclerView() {
        // Clear ang data sa DiseaseData
        DiseaseData.clearPieEntries();

        // Clear ang RecyclerView
        List<CategoryPercentageItem> categoryPercentageList = new ArrayList<>();
        CategoryPercentageAdapter adapter = new CategoryPercentageAdapter(categoryPercentageList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Ito ang bagong bahagi na idinagdag
    }




    private void initializePieChart() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(25f, "Disease 1"));
        entries.add(new PieEntry(35f, "Disease 2"));
        entries.add(new PieEntry(40f, "Disease 3"));
        entries.add(new PieEntry(60f, "Disease 4"));
        entries.add(new PieEntry(75f, "Disease 5"));
        entries.add(new PieEntry(80f, "Disease 6"));

        PieDataSet dataSet = new PieDataSet(entries, "Disease");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(12f);

        pieChart.getLegend().setEnabled(false);

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Disease");
        pieChart.animateY(1000);
    }

    private void initializeRecyclerView() {

        List<CategoryPercentageItem> categoryPercentageList = new ArrayList<>();
        List<PieEntry> entries = DiseaseData.getPieEntries();

        for (PieEntry entry : entries) {
            float percentage = entry.getValue();
            String diseaseName = entry.getLabel();
            String percentageString = String.format(Locale.getDefault(), "%.0f%%", percentage);

            categoryPercentageList.add(new CategoryPercentageItem(diseaseName, percentageString));
        }


        categoryPercentageList.add(new CategoryPercentageItem("Disease 1", "25%"));
        categoryPercentageList.add(new CategoryPercentageItem("Disease 2", "35%"));
        categoryPercentageList.add(new CategoryPercentageItem("Disease 3", "40%"));
        categoryPercentageList.add(new CategoryPercentageItem("Disease 4", "60%"));
        categoryPercentageList.add(new CategoryPercentageItem("Disease 5", "75%"));
        categoryPercentageList.add(new CategoryPercentageItem("Disease 6", "80%"));

        CategoryPercentageAdapter adapter = new CategoryPercentageAdapter(categoryPercentageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
