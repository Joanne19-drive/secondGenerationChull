package com.minji.underground.core.service;

import com.minji.underground.core.vo.SlackJson;
import com.minji.underground.core.vo.TrainInfo;
import com.minji.underground.slack.ResponseMap;
import com.minji.underground.slack.SlackService;
import com.minji.underground.subwayInfo.SubwayInfo;
import com.minji.underground.weatherInfo.WeatherInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Service
@Transactional
public class SubwayServiceImpl implements SubwayService {

    private final SlackService slackService;
    private final SubwayInfo subwayInfo;
    private final WeatherInfo weatherInfo;

    public SubwayServiceImpl(SlackService slackService, SubwayInfo subwayInfo, WeatherInfo weatherInfo) {
        this.slackService = slackService;
        this.subwayInfo = subwayInfo;
        this.weatherInfo = weatherInfo;
    }

    @Override
    public String subwayCongestionData(String stationName) throws IOException {
        if (stationName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<TrainInfo> arrivingTrains = subwayInfo.stationArrival(stationName);

        StringBuffer statusMessages = new StringBuffer();
        statusMessages.append("٩(๑❛ᴗ❛๑)۶ 실시간 혼잡도 ٩(๑❛ᴗ❛๑)۶ ");

        for (TrainInfo trainInfo : arrivingTrains) {
            String lineName = String.valueOf(trainInfo.lineNum.charAt(3));
            if (trainInfo.live) {
                String congestionRate = subwayInfo.liveSubwayCongestion(lineName, trainInfo.trainNum);
                List<String> congestionList = Arrays.asList(congestionRate.split("\\|"));
                statusMessages.append("\n").append(trainInfo.nextStation).append("으로 향하는 ").append(lineName).append("호선 열차 ")
                        .append(trainInfo.trainNum).append("의 실시간 혼잡도는 다음과 같습니다: ").append(congestionList);
            } else {
                int direction = 1;
                if (Objects.equals(trainInfo.direction, "상행") || Objects.equals(trainInfo.direction, "내선")) {
                    direction = 0;
                }
                List<String> congestionList = subwayInfo.subwayCongestionData(direction, trainInfo.stationCode, LocalDateTime.now().getMinute());
                statusMessages.append("\n").append(trainInfo.nextStation).append("으로 향하는 ").append(lineName).append("호선 열차 ").append(trainInfo.trainNum).append("의 예상 혼잡도는 다음과 같습니다: ").append(congestionList);
            }
        }

        return statusMessages.toString();
    }

    @Override
    public String slackEvent(Map<String, Object> data) throws IOException {
        SlackJson slackJson = new SlackJson(data);
        if (slackJson.getEventType().equals("app_mention")) {
            String[] textChunk = slackJson.getText().split(" ");
            String text = String.join(" ", Arrays.copyOfRange(textChunk, 1, textChunk.length));
            if (text.contains("지하철")) {
                List<String> subwayRequest = Arrays.asList(text.split(" "));
                String responseText = subwayCongestionData(subwayRequest.get(subwayRequest.size() - 1));
                slackJson.setResultText(responseText);
                slackService.sendMessage(responseText);
            } else if (text.contains("날씨 알려줘")){
                String responseText = weatherInfo.currentWeather();
                slackJson.setResultText(responseText);
                slackService.sendMessage(responseText);
            } else {
                String responseText = responseAnything(text, slackJson.getUser());
                slackJson.setResultText(responseText);
                slackService.sendMessage(responseText);
            }
        }
        slackJson.setJson();
        return slackJson.getJson();
    }

    @Override
    public String responseAnything(String text, String userId) {
        System.out.println(userId);
        String value = ResponseMap.getValue(text);

        if (value != null) {
            return value;
        } else if (text.equals("반가워")) {
            return "<@" + userId + "> 님 반가워요";
        } else {
            return "귀찮아서 대꾸하기 싫습니다. \n p.s. 제가 대답할 수 있는 단어는 [안녕?, 뭐해?, 정신차려!, 해고 당하고 싶어?, 바보, 윤회, " +
                    "상화, 승준, 현지, 민지, 비트박스, 메롱, 커리어팀, 달려!, 성공적, 고백에 성공하는 법, 에버랜드에서 재미있게 노는 법, " +
                    "반가워, 00 불러줘, 나한테 하고 싶은 말 없어?, 3대철이를 만들려면 어떻게 해야 돼?, 2대철이에게 이승준이란?, 배고파]입니다.";
        }
    }
}
