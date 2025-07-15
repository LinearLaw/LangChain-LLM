package com.linear.langchain.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeatherHandler {

    public String handle(String city){
        return city + "今天的天气37度，有点热";
    }

}
