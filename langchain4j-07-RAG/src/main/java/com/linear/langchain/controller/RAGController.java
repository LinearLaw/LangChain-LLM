package com.linear.langchain.controller;

import com.linear.langchain.service.ChatAssistant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("rag")
public class RAGController {

    @Resource
    private ChatAssistant chatAssistant;

    @GetMapping("search")
    public String search(@RequestParam("ask") String ask){

        // TODO

        return chatAssistant.chat(ask);
    }
}
