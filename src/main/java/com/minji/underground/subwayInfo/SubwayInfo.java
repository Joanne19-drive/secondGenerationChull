package com.minji.underground.subwayInfo;

import com.jayway.jsonpath.JsonPath;
import com.minji.underground.core.vo.TrainInfo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class SubwayInfo {
    @Value("${sk.subway.appKey}")
    private String skAppKey;

    @Value("${seoul.subway.appKey}")
    private String seoulAppKey;

    public String liveSubwayCongestion(String subwayLine, String trainCode) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://apis.openapi.sk.com/puzzle/subway/congestion/rltm/trains/" + subwayLine + "/" + trainCode;

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("appKey", skAppKey)
                .build();

        Response response = client.newCall(request).execute();

        if (response.code() != 200) {
            return "확인이 어려운 정보입니다.";
        }

        String body = response.body().string();

        return JsonPath.read(body, "$.data.congestionResult.congestionCar");
    }

    public List<String> subwayCongestionData(int direction, String stationCode, int min) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://apis.openapi.sk.com/puzzle/subway/congestion/stat/car/stations/" + stationCode;

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("appKey", skAppKey)
                .build();

        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            return Arrays.asList("확인이 어려운 정보입니다.");
        }

        String body = response.body().string();
        int checkMin = min / 10;

        List<Object> status = JsonPath.read(body, "$.contents.stat.*");
        List<String> result = null;
        for (Object s : status) {
            int updnLine = JsonPath.read(s, "$.updnLine");
            if (Objects.equals(direction, updnLine)) {
                result = JsonPath.read(s, String.format("$.data[%d].congestionCar.*", checkMin));
                if (!Objects.equals(result.get(0), "0")) {
                    break;
                }
            }
        }

        return result;
    }


    public List<TrainInfo> stationArrival(String stationName) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "http://swopenAPI.seoul.go.kr/api/subway/" + seoulAppKey +"/json/realtimeStationArrival/0/15/" + stationName;

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        Response response = client.newCall(request).execute();

        String body = response.body().string();

        List<Object> trains = JsonPath.read(body, "$.realtimeArrivalList.*");

        ArrayList<TrainInfo> arrivingTrains = new ArrayList<TrainInfo>();

        for (Object train : trains) {
            String lineNum = JsonPath.read(train, "$.subwayId");
            boolean live = Objects.equals(lineNum, "1002") || lineNum.equals("1003");
            if (lineNum.equals("1077")) {
                continue;
            }

            String ordkey = JsonPath.read(train, "$.ordkey");
            if (ordkey != null && ordkey.charAt(1) == '1') {
                String direction = JsonPath.read(train, "$.updnLine");
                String trainWay = JsonPath.read(train, "$.trainLineNm");
                String nextStation = Arrays.asList(trainWay.split(" - ")).get(1);
                String trainNum = arrangeNum(lineNum, JsonPath.read(train, "$.btrainNo"));
                String stationId = JsonPath.read(train, "$.statnId");
                TrainInfo trainInfo = new TrainInfo(stationName, direction, lineNum, nextStation, trainNum, live, stationId.substring(stationId.length() - 3));
                arrivingTrains.add(trainInfo);
            }
        }

        return arrivingTrains;
    }

    private static String arrangeNum(String lineNum, String num) {
        if (lineNum.equals("1002")) {
            return "2" + num.substring(1);
        } else {
            return num;
        }
    }
}
