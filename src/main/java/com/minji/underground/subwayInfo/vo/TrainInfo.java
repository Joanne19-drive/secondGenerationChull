package com.minji.underground.subwayInfo.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrainInfo {
    public String stationName;
    public String direction;
    public String lineNum;
    public String nextStation;
    public String trainNum;
    public Boolean live;
    public String stationCode;
}
