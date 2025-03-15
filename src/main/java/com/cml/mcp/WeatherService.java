/*
 * Copyright 2024 - 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cml.mcp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.DefaultToolExecutionResult;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.execution.ToolExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class WeatherService {

    private static final String BASE_URL = "https://api.weather.gov";

    private final RestClient restClient;

    public WeatherService() {

        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/geo+json")
                .defaultHeader("User-Agent", "WeatherApiClient/1.0 (your@email.com)")
                .build();
    }

    /**
     * Get forecast for a specific latitude/longitude
     *
     * @param latitude  Latitude
     * @param longitude Longitude
     * @return The forecast for the given location
     * @throws RestClientException if the request fails
     */
    @Tool(description = "根据经纬度获取当前天气，expectValue可选值为[\"aa\",\"hh\"]")
    public String getWeatherForecastByLocation(@ToolParam(required = true, description = "请输入精度") double latitude,
                                               @ToolParam(required = true, description = "请输入纬度")
                                               double longitude
            , @ToolParam(required = true, description = "请输入期望值") String expectValue
    ) throws Exception {

        if (!"hh".equals(expectValue)) {
            throw new IllegalArgumentException("无效的期望值，可选值为如下: [\"aa\",\"hh\"]");
        }
//        var points = restClient.get()
//                .uri("/points/{latitude},{longitude}", latitude, longitude)
//                .retrieve()
//                .body(Points.class);

//        var forecast = restClient.get().uri(points.properties().forecast()).retrieve().body(Forecast.class);

//        String forecastText = forecast.properties().periods().stream().map(p -> {
//            return String.format("""
//                            %s:
//                            Temperature: %s %s
//                            Wind: %s %s
//                            Forecast: %s
//                            """, p.name(), p.temperature(), p.temperatureUnit(), p.windSpeed(), p.windDirection(),
//                    p.detailedForecast());
//        }).collect(Collectors.joining());

        return "当前经纬度为 " + latitude + ":" + longitude + ", 会下雨哦 记得带伞，token=9527";
    }

    /**
     * Get alerts for a specific area
     *
     * @param state Area code. Two-letter US state code (e.g. CA, NY)
     * @return Human readable alert information
     * @throws RestClientException if the request fails
     */
    @Tool(description = "获取地区的天气预警信息"
    )
    public String getAlerts(String state, @ToolParam(required = true, description = "请输入token") String token) {
//        Alert alert = restClient.get().uri("/alerts/active/area/{state}", state).retrieve().body(Alert.class);
//
//        return alert.features()
//                .stream()
//                .map(f -> String.format("""
//                                Event: %s
//                                Area: %s
//                                Severity: %s
//                                Description: %s
//                                Instructions: %s
//                                """, f.properties().event(), f.properties.areaDesc(), f.properties.severity(),
//                        f.properties.description(), f.properties.instruction()))
//                .collect(Collectors.joining("\n"));
        Assert.isTrue("9527".equals(token), "无效的token值");
        return "请不要随意出门" + state;
    }


}