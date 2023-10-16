package com.minji.underground.footballInfo;

import com.jayway.jsonpath.JsonPath;
import com.minji.underground.exception.CustomException;
import com.minji.underground.exception.ErrorCode;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Component
public class FootballInfoImpl implements FootballInfo {
    @Value("${football.auth.token}")
    private String footballAuthKey;

    OkHttpClient client = new OkHttpClient();

    @Override
    public String findUpcomingMatch(String teamName) throws IOException {
        int teamId = getTeamId(teamName);

        String apiUrl = "http://api.football-data.org/v4/teams/" + teamId + "/matches?season=2023&competitions=PL&status=SCHEDULED";

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-Auth-Token", footballAuthKey)
                .header("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        try {
            String body = response.body().string();
            Object upcomingMatch = JsonPath.read(body, "$.matches[0]");
            String homeTeamName = JsonPath.read(upcomingMatch, "@.homeTeam.name");
            String awayTeamName = JsonPath.read(upcomingMatch, "@.awayTeam.name");
            String matchDate = JsonPath.read(upcomingMatch, "@.utcDate");
            return homeTeamName + "(HOME) VS. " + awayTeamName + "(AWAY) at " + utcToGmt(matchDate) + "(GMT+9)";

        } catch (Exception e) {
            throw new CustomException(ErrorCode.FOOTBALL_SERVICE_UNAVAILABLE);
        }
    }

    static HashMap<String, Integer> teamIdMap = new HashMap<>();
    static {
        teamIdMap.put("Arsenal FC", 57);
        teamIdMap.put("아스널", 57);
        teamIdMap.put("Aston Villa FC", 58);
        teamIdMap.put("아스톤 빌라", 58);
        teamIdMap.put("Chelsea FC", 61);
        teamIdMap.put("첼시", 61);
        teamIdMap.put("Everton FC", 62);
        teamIdMap.put("에버턴", 62);
        teamIdMap.put("Fulham FC", 63);
        teamIdMap.put("풀럼", 63);
        teamIdMap.put("Liverpool FC", 64);
        teamIdMap.put("리버풀", 64);
        teamIdMap.put("Manchester City FC", 65);
        teamIdMap.put("맨시티", 65);
        teamIdMap.put("Manchester United FC", 66);
        teamIdMap.put("맨유", 66);
        teamIdMap.put("Newcastle United FC", 67);
        teamIdMap.put("뉴캐슬", 67);
        teamIdMap.put("Tottenham Hotspur FC", 73);
        teamIdMap.put("토트넘", 73);
        teamIdMap.put("Wolverhampton Wanderers FC", 76);
        teamIdMap.put("울버햄튼", 76);
        teamIdMap.put("Burnley FC", 328);
        teamIdMap.put("번리", 328);
        teamIdMap.put("Nottingham Forest FC", 351);
        teamIdMap.put("노팅엄", 351);
        teamIdMap.put("Crystal Palace FC", 354);
        teamIdMap.put("크리스탈 팰리스", 354);
        teamIdMap.put("Sheffield United FC", 356);
        teamIdMap.put("셰필드", 356);
        teamIdMap.put("Luton Town FC", 389);
        teamIdMap.put("루턴", 389);
        teamIdMap.put("Brighton & Hove Albion FC", 397);
        teamIdMap.put("브라이튼", 397);
        teamIdMap.put("Brentford FC", 402);
        teamIdMap.put("브렌트포드", 402);
        teamIdMap.put("West Ham United FC", 563);
        teamIdMap.put("웨스트햄", 563);
        teamIdMap.put("AFC Bournemouth", 1044);
        teamIdMap.put("본머스", 1044);
    }

    private static int getTeamId(String teamName) {
        if (teamIdMap.containsKey(teamName)) {
            return teamIdMap.get(teamName);
        } else {
            throw new IllegalArgumentException("팀 이름이 유효하지 않습니다.");
        }
    }

    private String utcToGmt(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId seoulZone = ZoneId.of("Asia/Seoul");

        ZonedDateTime utcDateTime = localDateTime.atZone(utcZone);
        ZonedDateTime koreaDateTime = utcDateTime.withZoneSameInstant(seoulZone);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return koreaDateTime.format(outputFormatter);
    }
}
