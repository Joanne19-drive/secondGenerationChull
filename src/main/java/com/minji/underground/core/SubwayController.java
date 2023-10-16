package com.minji.underground.core;

import com.minji.underground.core.service.SubwayService;
import com.minji.underground.slack.SlackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class SubwayController {

    private final SubwayService subwayService;
    private final SlackService slackService;

    public SubwayController(SubwayService subwayService, SlackService slackService) {
        this.subwayService = subwayService;
        this.slackService = slackService;
    }

    @PostMapping("/subway")
    public ResponseEntity<String> liveSubwayStatus(@RequestParam String stationName) throws IOException {
        return ResponseEntity.ok()
                .body(subwayService.subwayCongestionData(stationName));
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

    @GetMapping("/football")
    public ResponseEntity<String> footballMatches() throws IOException {
        return ResponseEntity.ok()
                .body(subwayService.footballData());
    }

    public void slackMessage(@RequestBody String message) {
        slackService.sendMessage(message);
    }
}
