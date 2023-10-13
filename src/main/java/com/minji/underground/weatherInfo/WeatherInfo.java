package com.minji.underground.weatherInfo;

import com.jayway.jsonpath.JsonPath;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class WeatherInfo {
    @Value("${korea.weather.appKey}")
    private String weatherAppKey;

    OkHttpClient client = new OkHttpClient();

    public String currentWeather() throws IOException {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);

        String apiUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst?serviceKey=" + weatherAppKey +
                "&pageNo=1&numOfRows=10&dataType=json&stnId=108&tmFc=" + formatedNow + "0600";

        System.out.println(apiUrl);
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        Response response = client.newCall(request).execute();

        String body = response.body().string();
        System.out.println(body);
        return JsonPath.read(body, "$.response.body.items.item[0].wfSv");
    }
}
