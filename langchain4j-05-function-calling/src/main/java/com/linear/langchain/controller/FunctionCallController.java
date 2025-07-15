package com.linear.langchain.controller;

import com.linear.langchain.service.FunctionAssistant;
import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("function")
@Slf4j
public class FunctionCallController {

    @Resource
    private ChatModel chatModel;

    @Resource(name = "lower")
    private FunctionAssistant functionAssistant;

    /**
     * localhost:8080/function/call?ask=今天天气如何
     * @param ask
     * @return
     */
    @GetMapping("call")
    private String functionCalling(@RequestParam("ask") String ask){
        log.info(ask);
        return chatModel.chat(ask);
    }

    /**
     * localhost:8080/function/assistant?ask=今天天气如何
     * @param ask
     * @return
     */
    @GetMapping("assistant")
    private String useFunctionAssistant(@RequestParam("ask") String ask){
        log.info(ask);
        return functionAssistant.chat(ask);
    }

}
