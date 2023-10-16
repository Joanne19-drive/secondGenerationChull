package com.minji.underground.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // 400

    // 404
    STATION_NAME_NOT_FOUND(404, "혼잡도 정보를 제공할 수 없는 역입니다."),

    // 503
    SUBWAY_SERVICE_UNAVAILABLE(503, "일시적으로 지하철 혼잡도 서비스를 제공할 수 없습니다."),
    WEATHER_SERVICE_UNAVAILABLE(503, "일시적으로 날씨 서비스를 제공할 수 없습니다.");

    private final int statusCode;
    private final String message;
}
