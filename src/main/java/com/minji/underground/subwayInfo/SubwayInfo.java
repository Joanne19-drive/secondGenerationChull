package com.minji.underground.subwayInfo;

import com.minji.underground.subwayInfo.vo.TrainInfo;

import java.io.IOException;
import java.util.List;

public interface SubwayInfo {
    String subwayCongestionData(String stationName) throws IOException;
    String liveSubwayCongestion(String subwayLine, String trainCode) throws IOException;
    List<String> staticSubwayCongestion(int direction, String stationCode, int min) throws IOException;
    List<TrainInfo> stationArrival(String stationName) throws IOException;

}
