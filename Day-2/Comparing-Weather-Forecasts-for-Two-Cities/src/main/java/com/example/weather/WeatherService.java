package com.example.weather;

import com.example.weather.WeatherForecast;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<WeatherForecast> getWeatherForecast(double latitude, double longitude) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("daily", "temperature_2m_max,temperature_2m_min,precipitation_sum,wind_speed_10m_max")
                .queryParam("timezone", "auto")
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        return parseWeatherResponse(response);
    }

    private List<WeatherForecast> parseWeatherResponse(Map<String, Object> response) {
        List<WeatherForecast> forecasts = new ArrayList<>();
        Map<String, Object> daily = (Map<String, Object>) response.get("daily");

        List<String> dates = (List<String>) daily.get("time");
        List<Double> maxTemperatures = (List<Double>) daily.get("temperature_2m_max");
        List<Double> minTemperatures = (List<Double>) daily.get("temperature_2m_min");
        List<Double> precipitation = (List<Double>) daily.get("precipitation_sum");
        List<Double> windSpeeds = (List<Double>) daily.get("wind_speed_10m_max");

        for (int i = 0; i < dates.size(); i++) {
            double avgTemperature = (maxTemperatures.get(i) + minTemperatures.get(i)) / 2;
            forecasts.add(new WeatherForecast(LocalDate.parse(dates.get(i)), avgTemperature, precipitation.get(i), windSpeeds.get(i)));
        }

        return forecasts;
    }

    public String compareWeather(List<WeatherForecast> city1, List<WeatherForecast> city2) {
        StringBuilder result = new StringBuilder("Weather Comparison for 7 Days:\n");

        for (int i = 0; i < city1.size(); i++) {
            result.append("Date: ").append(city1.get(i).getDate()).append("\n");
            result.append("City 1 - Temperature: ").append(city1.get(i).getTemperature())
                    .append(", Humidity: ").append(city1.get(i).getHumidity())
                    .append(", Wind Speed: ").append(city1.get(i).getWindSpeed()).append("\n");

            result.append("City 2 - Temperature: ").append(city2.get(i).getTemperature())
                    .append(", Humidity: ").append(city2.get(i).getHumidity())
                    .append(", Wind Speed: ").append(city2.get(i).getWindSpeed()).append("\n\n");
        }

        return result.toString();
    }
}
