package com.minji.underground.footballInfo;

import java.io.IOException;

public interface FootballInfo {
    String findUpcomingMatch(String teamName) throws IOException;
    String matchResult(String teamName) throws IOException;
}
