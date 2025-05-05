package com.weather.api.service;

import com.weather.api.model.WeatherCallDetails;
import com.weather.api.repository.WeatherCallDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final RestTemplate restTemplate = new RestTemplate();

    private final WeatherCallDetailsRepository repository;


    @Value("{openweathermap.api.key}")
    private String apiKey;

    @Value("{openweathermap.geo.url}")
    private String geoUrlTemplate;

    @Value("{openweathermap.weather.url}")
    private String weatherUrlTemplate;


//    public Map<String, Object> getWeather(String zipCode, HttpServletRequest request) {
//        // Call Open-Meteo Geocoding API to get lat/lon based on ZIP code
//        String geoUrl = String.format("https://geocoding-api.open-meteo.com/v1/search?name=%s&country=US", zipCode);
//        Map<String, Object> geoData = restTemplate.getForObject(geoUrl, Map.class);
//
//        // Check if the response contains latitude and longitude
//        if (geoData == null || geoData.get("results") == null || ((List) geoData.get("results")).isEmpty()) {
//            throw new RuntimeException("Invalid ZIP code or failed to fetch geo data.");
//        }
//
//        // Extract latitude and longitude from the response
//        Map<String, Object> location = ((List<Map<String, Object>>) geoData.get("results")).get(0);
//        String lat = location.get("latitude").toString();
//        String lon = location.get("longitude").toString();
//
//        // Call Open-Meteo Weather API using the obtained lat and lon for current weather data
//        String weatherUrl = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true", lat, lon);
//        Map<String, Object> weatherData = restTemplate.getForObject(weatherUrl, Map.class);
//
//        // Save weather call details to the repository
//        repository.save(WeatherCallDetails.builder()
//                .zipCode(zipCode)
//                .requesterIp(request.getRemoteAddr())
//                .timestamp(LocalDateTime.now())
//                .build());
//
//        // Return the weather data
//        return weatherData;


    public Map<String, Object> getWeather(String zipCode, HttpServletRequest request) {
        // Step 1: Get coordinates
        Map<String, String> coordinates = getCoordinatesForZip(zipCode);

        // Step 2: Get weather data
        Map<String, Object> weatherData = getWeatherByCoordinates(coordinates.get("lat"), coordinates.get("lon"));

        // Save weather call details to the repository
        repository.save(WeatherCallDetails.builder()
                .zipCode(zipCode)
                .requesterIp(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build());

        return weatherData;
    }

    private Map<String, String> getCoordinatesForZip(String zipCode) {
        String geoUrl = String.format("https://geocoding-api.open-meteo.com/v1/search?name=%s&country=US", zipCode);
        Map<String, Object> geoData = restTemplate.getForObject(geoUrl, Map.class);

        if (geoData == null || geoData.get("results") == null || ((List<?>) geoData.get("results")).isEmpty()) {
            throw new RuntimeException("Invalid ZIP code or failed to fetch geo data.");
        }

        Map<String, Object> location = ((List<Map<String, Object>>) geoData.get("results")).get(0);
        String lat = location.get("latitude").toString();
        String lon = location.get("longitude").toString();

        Map<String, String> lat_long = new HashMap<>();
        lat_long.put("lat", lat);
        lat_long.put("lon", lon);

        return lat_long;
    }

    private Map<String, Object> getWeatherByCoordinates(String lat, String lon) {
        String weatherUrl = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true", lat, lon);
        return restTemplate.getForObject(weatherUrl, Map.class);
    }
}