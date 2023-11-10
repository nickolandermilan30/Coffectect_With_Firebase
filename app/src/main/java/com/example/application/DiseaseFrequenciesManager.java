package com.example.application;

import java.util.HashMap;
import java.util.Map;

public class DiseaseFrequenciesManager {

    private Map<String, Integer> diseaseFrequencies = new HashMap<>();

    public void updateFrequency(String diseaseName) {
        diseaseFrequencies.put(diseaseName, diseaseFrequencies.getOrDefault(diseaseName, 0) + 1);
    }

    public String getMostFrequentDisease() {
        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseFrequencies.entrySet()) {
            String diseaseName = entry.getKey();
            int frequency = entry.getValue();

            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                mostFrequentDisease = diseaseName;
            }
        }

        return mostFrequentDisease;
    }

    public void clearFrequencies() {
        diseaseFrequencies.clear();
    }
}
