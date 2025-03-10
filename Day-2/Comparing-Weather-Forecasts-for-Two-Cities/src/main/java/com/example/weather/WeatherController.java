package com.example.weather;

import com.example.weather.WeatherForecast;
import com.example.weather.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/forecast")
    public List<WeatherForecast> getForecast(@RequestParam double latitude, @RequestParam double longitude) {
        return weatherService.getWeatherForecast(latitude, longitude);
    }

    @GetMapping("/compare")
    public String compareForecasts(
            @RequestParam double lat1, @RequestParam double lon1,
            @RequestParam double lat2, @RequestParam double lon2) {

        List<WeatherForecast> city1Forecast = weatherService.getWeatherForecast(lat1, lon1);
        List<WeatherForecast> city2Forecast = weatherService.getWeatherForecast(lat2, lon2);

        return weatherService.compareWeather(city1Forecast, city2Forecast);
    }
}
