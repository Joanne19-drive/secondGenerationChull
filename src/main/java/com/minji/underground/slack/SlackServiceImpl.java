package com.minji.underground.slack;

import org.json.simple.JSONObject;
import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SlackServiceImpl implements SlackService {
    @Value("${webhook.slack.url}")
    private String SLACK_WEBHOOK_URL;

    private final Slack slackClient = Slack.getInstance();

    @Override
    public void sendMessage(String message) {
        JSONObject json = new JSONObject();
        json.put("text", message);
        String payload = json.toJSONString();

        try {
            WebhookResponse response = slackClient.send(SLACK_WEBHOOK_URL, payload);
            System.out.println(response);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String responseAnything(String text, String userId) {
        if (userId.equals("U058V83P8LU")) {
            return "이승준 넌 제명이야";
        }

        String value = ResponseMap.getValue(text);

        if (value != null) {
            return value;
        } else if (text.equals("반가워")) {
            return "<@" + userId + "> 님 반가워요";
        } else if (userId.equals("U059GEF25DW")){
            return "구윤회 같은 질문 금지";
        } else {
            return "그런 건 에이닷 프렌즈 친구들과 이야기해보지 않으실래요?";
        }
    }
}
