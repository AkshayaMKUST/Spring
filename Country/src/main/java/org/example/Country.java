package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Country {
    public String findCountry(){
        List<String> countries = new ArrayList<>();
        countries.add("Denmark");
        countries.add("India");
        countries.add("Singapore");
        countries.add("United Arab Emirates");

        Optional<String> longestCountry = countries.stream().reduce((country1, country2)->country1.length()>country2.length() ? country1:country2);
        return longestCountry.get();
    }
}
