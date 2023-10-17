package com.minji.underground.core;

import com.minji.underground.core.service.ChullService;
import com.minji.underground.slack.SlackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ChullBotController {

    private final ChullService subwayService;
    private final SlackService slackService;

    public ChullBotController(ChullService subwayService, SlackService slackService) {
        this.subwayService = subwayService;
        this.slackService = slackService;
    }

    @PostMapping("/slack/event")
    public ResponseEntity<String> event(@RequestBody Map<String, Object> data) {
        try {
            String result = subwayService.slackEvent(data);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/slack/message")
    public void slackMessage(@RequestBody String message) {
        slackService.sendMessage(message);
    }
}
