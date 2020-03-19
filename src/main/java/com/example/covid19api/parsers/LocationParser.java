package com.example.covid19api.parsers;

import com.example.covid19api.data.Data;
import com.example.covid19api.model.Location;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationParser {
    private Data data = new Data();

    // Containers for records
    private List<String> lastUpdates = new ArrayList<>();
    private List<String> countries = new ArrayList<>();
    private List<String> regions = new ArrayList<>();
    private List<Double> lats = new ArrayList<>();
    private List<Double> lons = new ArrayList<>();
    private List<Integer> confirmedCases = new ArrayList<>();
    private List<Integer> deathCases = new ArrayList<>();
    private List<Integer> recoveredCases = new ArrayList<>();

    public String parseData(int index) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Readers
        StringReader readerLastUpdate = new StringReader(data.getData());
        StringReader readerCountry = new StringReader(data.getData());
        StringReader readerRegion = new StringReader(data.getData());
        StringReader readerLat = new StringReader(data.getData());
        StringReader readerLon = new StringReader(data.getData());
        StringReader readerConfirmed = new StringReader(data.getData());
        StringReader readerDeaths = new StringReader(data.getData());
        StringReader readerRecovered = new StringReader(data.getData());

        // Parsers
        CSVParser parserLastUpdate;
        CSVParser parserCountry;
        CSVParser parserRegion;
        CSVParser parserLat;
        CSVParser parserLon;
        CSVParser parserConfirmed;
        CSVParser parserDeaths;
        CSVParser parserRecovered;

        try {
            parserLastUpdate = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerLastUpdate);
            parserCountry = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerCountry);
            parserRegion = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerRegion);
            parserLat = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerLat);
            parserLon = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerLon);
            parserConfirmed = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerConfirmed);
            parserDeaths = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerDeaths);
            parserRecovered = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerRecovered);

            for (CSVRecord strings : parserLastUpdate) {
                lastUpdates.add(strings.get("Last Update"));
            }

            for (CSVRecord strings : parserCountry) {
                countries.add(strings.get("Country/Region"));
            }

            for (CSVRecord strings : parserRegion) {
                regions.add(strings.get("Province/State"));
            }

            for (CSVRecord strings : parserLat) {
                lats.add(Double.parseDouble(strings.get("Latitude")));
            }

            for (CSVRecord strings : parserLon) {
                lons.add(Double.parseDouble(strings.get("Longitude")));
            }

            for (CSVRecord strings : parserConfirmed) {
                confirmedCases.add(Integer.parseInt(strings.get("Confirmed")));
            }

            for (CSVRecord strings : parserDeaths) {
                deathCases.add(Integer.parseInt(strings.get("Deaths")));
            }

            for (CSVRecord strings : parserRecovered) {
                recoveredCases.add(Integer.parseInt(strings.get("Recovered")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Location model = new Location(
                lastUpdates.get(index),
                countries.get(index),
                regions.get(index),
                lats.get(index),
                lons.get(index),
                confirmedCases.get(index),
                deathCases.get(index),
                recoveredCases.get(index)
        );
        return gson.toJson(model);
    }

    public List<String> getLastUpdates() {
        return lastUpdates;
    }

    public List<String> getCountries() {
        return countries;
    }

    public List<String> getRegions() {
        return regions;
    }

    public List<Double> getLats() {
        return lats;
    }

    public List<Double> getLons() {
        return lons;
    }

    public List<Integer> getConfirmedCases() {
        return confirmedCases;
    }

    public List<Integer> getDeathCases() {
        return deathCases;
    }

    public List<Integer> getRecoveredCases() {
        return recoveredCases;
    }
}