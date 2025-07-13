package com.linear.langchain.controller;

import com.linear.langchain.service.ChatPersistenceAssistant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("persistence")
@Slf4j
public class ChatPersistenceController {

    @Resource
    private ChatPersistenceAssistant assistant;

    /**
     * http://localhost:8080/persistence/redis?ask=微软的CEO是谁
     */
    @GetMapping("redis")
    public String useRedisStore(@RequestParam("ask") String ask,
                                @RequestParam(value = "userId", defaultValue = "1") Long userId){
        return assistant.chat(userId, ask);
    }

}