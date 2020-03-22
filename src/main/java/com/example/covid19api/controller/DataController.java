package com.example.covid19api.controller;

import com.example.covid19api.service.DataService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api")
public class DataController {
    private Gson gson = new Gson();

    private DataService dataService = new DataService();

    @GetMapping("/actual")
    public String actual() {
        return dataService.getActualData();
    }

    @GetMapping("/actual/location")
    public String searchActualDataByLocation(@RequestParam(required = false) String country, @RequestParam(required = false) String region) {
        if (country != null && region != null) {
            return this.dataService.searchDataByCountryAndRegion(country, region);
        } else if (country != null) {
            return this.dataService.searchDataByCountry(country);
        } else if (region != null) {
            return this.dataService.searchDataByRegion(region);
        } else {
            Map<String, String> errorMessage = new TreeMap<>();
            errorMessage.put("message", "Data not found!");
            return gson.toJson(errorMessage);
        }
    }

    @GetMapping("/history/{date}")
    public String history(@PathVariable String date) {
        return dataService.getHistoricalData(date);
    }
}
