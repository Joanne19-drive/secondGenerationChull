package com.minji.underground.core.service;

import java.io.IOException;
import java.util.Map;

public interface SubwayService {
    String subwayCongestionData(String stationName) throws IOException;
    String slackEvent(Map<String, Object> data) throws IOException;
    String responseAnything(String text, String userId);
    String footballData() throws IOException;
}
