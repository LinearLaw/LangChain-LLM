package com.linear.langchain.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MultiModelController {

    @Resource(name = "qwen")
    private ChatModel chatModelQwen;

    @Resource(name = "deepseek")
    private ChatModel chatModelDeepseek;

    /**
     * http://localhost:8080/langchain4j/qwen?ask=你是谁
     */
    @GetMapping("/langchain4j/qwen")
    public String askQwen(@RequestParam(name = "ask", defaultValue = "你是谁") String ask){
        String result = chatModelQwen.chat(ask);
        System.out.println(result);
        return result;
    }

    /**
     * http://localhost:8080/langchain4j/deepseek?ask=你是谁
     */
    @GetMapping("/langchain4j/deepseek")
    public String askDeepseek(@RequestParam(name = "ask", defaultValue = "你是谁") String ask){
        String result = chatModelDeepseek.chat(ask);
        System.out.println(result);
        return result;
    }


}
