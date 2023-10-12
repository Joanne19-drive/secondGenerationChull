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
}
