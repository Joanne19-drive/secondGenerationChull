package com.minji.underground.core.service;

import com.minji.underground.core.vo.SlackJson;
import com.minji.underground.core.vo.TrainInfo;
import com.minji.underground.slack.SlackService;
import com.minji.underground.subwayInfo.SubwayInfo;
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

    public SubwayServiceImpl(SlackService slackService, SubwayInfo subwayInfo) {
        this.slackService = slackService;
        this.subwayInfo = subwayInfo;
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
            } else {
                String responseText = responseAnything(text, slackJson.getUser());
                slackService.sendMessage(responseText);
            }
        }
        slackJson.setJson();
        return slackJson.getJson();
    }

    @Override
    public String responseAnything(String text, String userId) {
        System.out.println(userId);
        if (text.equals("안녕?")) {
            return "안녕하세요! 2대철이에오.";
        } else if (text.equals("뭐해?")) {
            return "머리 식히는 중이에오.";
        } else if (text.equals("정신차려!")) {
            return "노력하겠습니다.";
        } else if (text.equals("해고 당하고 싶어?")) {
            return "갑질하지 마세오.";
        } else if (text.equals("바보")) {
            return "는 너";
        } else if (text.equals("윤회")) {
            return "솔로탈출 성공!";
        } else if (text.equals("상화")) {
            return "겸둥이 막내~";
        } else if (text.equals("승준")) {
            return "살아..있으신가..요..?";
        } else if (text.equals("현지")) {
            return "척척석사 그잡채";
        } else if (text.equals("민지")) {
            return "철이 유니버스 창시자";
        } else if (text.equals("비트박스")) {
            return "움치키둠치키 치기지기 자가자가장";
        } else if (text.equals("메롱")) {
            return "유치한 건 이제 그만..";
        } else if (text.equals("커리어팀")) {
            return "4명이 딱이지?";
        } else if (text.equals("달려!")) {
            return "우다다다다다다다다";
        } else if (text.equals("구윤회")) {
            return "진실을 밝혀라 우우";
        } else if (text.equals("성공적")) {
            return "와칸다 포에버";
        } else if (text.equals("고백에 성공하는 법")) {
            return "일단 해보고 말할래?";
        } else if (text.equals("에버랜드에서 재미있게 노는 법")) {
            return "커리어팀과 함께 가기";
        } else if (text.equals("떠나요 둘이서~")) {
            return "모든 걸 훌훌 버리고~";
        } else if (text.equals("반가워")) {
            return "<@" + userId + "> 님 반가워요";
        }
        return "귀찮아서 대꾸하기 싫습니다. \n p.s. 제가 대답할 수 있는 단어는 [안녕?, 뭐해?, 정신차려!, 해고 당하고 싶어?, 바보, 윤회, 상화, 승준, 현지, 민지, 비트박스, 메롱, 커리어팀, 달려!, 성공적, 고백에 성공하는 법, 에버랜드에서 재미있게 노는 법, 반가워]입니다.";
    }
}
