package com.minji.underground.service;

import com.minji.underground.core.service.SubwayServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class SubwayServiceTest {
    @InjectMocks
    SubwayServiceImpl subwayService;

    @Test
    @DisplayName("일반 대화 성공")
    void talkWithUser1() throws IOException {
        // given
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("type", "app_mention");
        eventData.put("user", "W021FGA1Z");
        eventData.put("text", "미아리나 안녕?");
        eventData.put("ts", 1515449483.000108);
        eventData.put("channel", "C0595CFAMH7");
        eventData.put("event_ts", 1697084161.801609);

        List<String> authedUsers = new ArrayList<>();
        authedUsers.add("U0LAN0Z89");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("token", "f3BunMoaSJPQvXubCjFFEmer");
        requestBody.put("team_id", "T058SP71WR0");
        requestBody.put("api_app_id", "A060MPSMN90");
        requestBody.put("event", eventData);
        requestBody.put("type", "event_callback");
        requestBody.put("event_id", "Ev060VQ87S6Q");
        requestBody.put("event_time", "1697084161");
        requestBody.put("authed_users", authedUsers);

        // when
        String response = subwayService.slackEvent(requestBody);

        // then
        System.out.println(response);
    }
}
