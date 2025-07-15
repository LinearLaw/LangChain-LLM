package com.linear.langchain.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("function")
public class FunctionCallController {

    @Resource
    private ChatModel chatModel;

    /**
     * localhost:8080/function/call?ask=今天天气如何
     * @param ask
     * @return
     */
    @GetMapping("call")
    private String functionCalling(@RequestParam("ask") String ask){
        System.out.println(ask);
        return chatModel.chat(ask);
    }

}
