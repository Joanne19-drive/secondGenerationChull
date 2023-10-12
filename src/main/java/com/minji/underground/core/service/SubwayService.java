package com.minji.underground.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SubwayService {
    List<String> subwayCongestionData(String stationName) throws IOException;
    String slackEvent(Map<String, Object> data) throws IOException;
    String responseAnything(String text, String userId);
}
