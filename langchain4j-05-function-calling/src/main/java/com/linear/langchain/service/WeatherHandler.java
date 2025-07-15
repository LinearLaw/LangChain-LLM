package com.linear.langchain.service;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeatherHandler {

    @Tool("根据用户的询问查找对应城市的天气信息")
    public String searchWeather(@P("城市") String city){
        return city + "今天的天气37度，晴，有点热";
    }

}
