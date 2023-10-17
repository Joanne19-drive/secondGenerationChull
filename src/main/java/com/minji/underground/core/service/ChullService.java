package com.minji.underground.core.service;

import java.io.IOException;
import java.util.Map;

public interface ChullService {
    String slackEvent(Map<String, Object> data) throws IOException;
}
