package com.evil.inc.githuber.service.impl;

import com.evil.inc.githuber.domain.City;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public enum CityCoordinatesManager {

    INSTANCE;

    private final Set<City> cities = init();

    private Set<City> init() {
        Set<City> cities = new HashSet<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/countries.csv"))) {
            String[] r = null;
            while ((r = csvReader.readNext()) != null) {
                cities.add(new City(r[0], r[1], r[4], r[5], r[6], Double.parseDouble(r[3]), Double.parseDouble(r[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public Set<City> getCities() {
        return cities;
    }

    public City getCoordinates(String location) {
        if (location != null && location.contains(",")) {
            return getCoordinates(location.split(",")[0]);
        }
        return cities.stream().filter(c ->
                c.getCity().equals(location)
                        || c.getCityASCII().equals(location)
                        || c.getCodeISO2().equals(location)
                        || c.getCodeISO3().equals(location)
                        || c.getCountry().equals(location))
                .findFirst()
                .orElse(new City("n/a", "n/a", "n/a", "n/a", "n/a", 4.6126, -74.0705)); //Bogota - liked the name
    }
}
