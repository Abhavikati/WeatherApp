package com.weather.api.controller;

import com.weather.api.service.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/{zip}")
    public ResponseEntity<?> getWeather(@PathVariable("zip") String zip, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(weatherService.getWeather(zip, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
