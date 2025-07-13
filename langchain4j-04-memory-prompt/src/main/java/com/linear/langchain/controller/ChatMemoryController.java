package com.linear.langchain.controller;


import com.linear.langchain.service.ChatAssistant;
import com.linear.langchain.service.ChatMemoryAssistant;
import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("memory")
@Slf4j
public class ChatMemoryController {

    @Resource
    private ChatAssistant chatAssistant;

    @Resource(name = "chatMessageWindow")
    private ChatMemoryAssistant chatMessageWindow;

    @Resource(name = "chatTokenWindow")
    private ChatMemoryAssistant chatTokenWindow;

    /**
     * http://localhost:8080/memory/without
     */
    @GetMapping("/without")
    public List<Map<String, String>> chatWithoutMemory(){
        String q1 = "你好，我的名字是Linear";
        log.info("Q1: {}", q1);
        String a1 = chatAssistant.chat(q1);
        log.info("A1: {}", a1);

        String q2 = "我的名字叫什么？";
        log.info("Q2: {}", q2);
        String a2 = chatAssistant.chat(q2);
        log.info("A2: {}", a2);

        return List.of(
                Map.of(q1, a1),
                Map.of(q2, a2)
        );
    }

    /**
     * http://localhost:8080/memory/message/window
     */
    @GetMapping("/message/window")
    public List<Map<String, String>> chatWithMessageWindowMemory(){
        String u1 = "[UserId=1]";
        String q11 = "你好，我的名字是Linear";
        log.info("{}Q1: {}", u1, q11);
        String a11 = chatMessageWindow.chatWithChatMemory(1L, q11);
        log.info("{}A1: {}", u1, a11);

        String u2 = "[UserId=2]";
        String q12 = "你好，我的名字是Sunny";
        log.info("{}Q1: {}", u2, q12);
        String a12 = chatMessageWindow.chatWithChatMemory(2L, q12);
        log.info("{}A1: {}", u2, a12);

        String q2 = "我的名字叫什么？";
        log.info("Q2: {}", q2);
        String a21 = chatMessageWindow.chatWithChatMemory(1L, q2);
        log.info("{}A2: {}", u1, a21);
        String a22 = chatMessageWindow.chatWithChatMemory(2L, q2);
        log.info("{}A2: {}", u2, a22);

        return List.of(
                Map.of(u1+q11, a11 , u1+q2, a21),
                Map.of(u2+q12, a12, u2+q2, a22)
        );
    }

    /**
     * http://localhost:8080/memory/token/window
     */
    @GetMapping("/token/window")
    public List<Map<String, String>> chatWithTokenWindowMemory(){
        String u1 = "[UserId=1]";
        String q11 = "你好，我的名字是Linear";
        log.info("{}Q1: {}", u1, q11);
        String a11 = chatTokenWindow.chatWithChatMemory(1L, q11);
        log.info("{}A1: {}", u1, a11);

        String u2 = "[UserId=2]";
        String q12 = "你好，我的名字是Sunny";
        log.info("{}Q1: {}", u2, q12);
        String a12 = chatTokenWindow.chatWithChatMemory(2L, q12);
        log.info("{}A1: {}", u2, a12);

        String q2 = "我的名字叫什么？";
        log.info("Q2: {}", q2);
        String a21 = chatTokenWindow.chatWithChatMemory(1L, q2);
        log.info("{}A2: {}", u1, a21);
        String a22 = chatTokenWindow.chatWithChatMemory(2L, q2);
        log.info("{}A2: {}", u2, a22);

        return List.of(
                Map.of(u1+q11, a11 , u1+q2, a21),
                Map.of(u2+q12, a12, u2+q2, a22)
        );
    }
}
