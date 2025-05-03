package com.weather.api.repository;

import com.weather.api.model.WeatherCallDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherCallDetailsRepository extends JpaRepository<WeatherCallDetails, Long> {
}
