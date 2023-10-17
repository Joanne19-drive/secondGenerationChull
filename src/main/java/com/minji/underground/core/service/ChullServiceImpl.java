package com.minji.underground.core.service;

import com.minji.underground.slack.vo.SlackJson;
import com.minji.underground.footballInfo.FootballInfo;
import com.minji.underground.slack.SlackService;
import com.minji.underground.subwayInfo.SubwayInfo;
import com.minji.underground.weatherInfo.WeatherInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;


@Service
@Transactional
public class ChullServiceImpl implements ChullService {

    private final SlackService slackService;
    private final SubwayInfo subwayInfo;
    private final WeatherInfo weatherInfo;
    private final FootballInfo footballInfo;

    public ChullServiceImpl(SlackService slackService, SubwayInfo subwayInfo, WeatherInfo weatherInfo, FootballInfo footballInfo) {
        this.slackService = slackService;
        this.subwayInfo = subwayInfo;
        this.weatherInfo = weatherInfo;
        this.footballInfo = footballInfo;
    }



    @Override
    public String slackEvent(Map<String, Object> data) throws IOException {
        SlackJson slackJson = new SlackJson(data);
        if (slackJson.getEventType().equals("app_mention")) {
            String[] textChunk = slackJson.getText().split(" ");
            String text = String.join(" ", Arrays.copyOfRange(textChunk, 1, textChunk.length));
            String responseText;
            if (text.contains("지하철")) {
                List<String> subwayRequest = Arrays.asList(text.split(" "));
                responseText = subwayInfo.subwayCongestionData(subwayRequest.get(subwayRequest.size() - 1));
            } else if (text.contains("날씨 알려줘")){
                responseText = weatherInfo.currentWeather();
            } else if (text.contains("경기 일정 알려줘")){
                List<String> footballRequest = Arrays.asList(text.split(" "));
                responseText = footballInfo.findUpcomingMatch(footballRequest.get(0));
            } else if (text.contains("경기 결과 알려줘")) {
                List<String> footballRequest = Arrays.asList(text.split(" "));
                responseText = footballInfo.matchResult(footballRequest.get(0));
            } else {
                responseText = slackService.responseAnything(text, slackJson.getUser());
            }
            slackJson.setResultText(responseText);
            slackService.sendMessage(responseText);
        }
        slackJson.setJson();
        return slackJson.getJson();
    }
}
