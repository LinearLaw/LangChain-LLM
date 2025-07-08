package com.linear.langchain.controller;


import com.linear.langchain.service.ChatAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiServiceController {

    @Resource
    private ChatAssistant assistant;

    /**
     * http://localhost:8080/chat/assistant?ask=hello
     * @param ask
     * @return
     */
    @GetMapping("/chat/assistant")
    public String chatWithAssistant(@RequestParam(name = "ask", defaultValue = "你是谁") String ask){
        return assistant.chat(ask);
    }
}
