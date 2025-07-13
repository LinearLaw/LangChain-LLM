package com.linear.langchain.controller;

import com.alibaba.dashscope.utils.JsonUtils;
import com.linear.langchain.entities.LegalWorkerPrompt;
import com.linear.langchain.service.CharacterAssistant;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("prompt")
@Slf4j
public class PromptController {

    @Resource
    private ChatModel chatModel;

    @Resource
    private CharacterAssistant characterAssistant;

    /**
     * http://localhost:8080/prompt/annotation?ask=什么罪名会导致无期徒刑
     * http://localhost:8080/prompt/annotation?ask=今天天气如何
     * 设定：只回答法律问题，其他问题一概拒绝
     */
    @GetMapping("annotation")
    public String useAnnotation(@RequestParam("ask") String ask){
        return characterAssistant.chatAsLegalWorker(ask, 300);
    }

    /**
     * http://localhost:8080/prompt/entity?ask=闯红灯触犯法律吗
     * http://localhost:8080/prompt/entity?ask=一天需要喝多少杯水
     * 设定：只回答法律问题，其他问题一概拒绝
     */
    @GetMapping("entity")
    public String useEntity(@RequestParam("ask") String ask){
        LegalWorkerPrompt legalWorkerPrompt = new LegalWorkerPrompt();
        legalWorkerPrompt.setCount(300);
        legalWorkerPrompt.setLegal("刑法");
        legalWorkerPrompt.setQuestion(ask);

        return characterAssistant.chatAsLegalWorkerByEntity(legalWorkerPrompt);
    }

    /**
     * http://localhost:8080/prompt/template?ask=中国四大名著在哪个货架？
     * http://localhost:8080/prompt/template?ask=红楼梦总共有多少回？
     * 设定：只回答法律问题，其他问题一概拒绝
     */
    @GetMapping("template")
    public String usePromptTemplate(@RequestParam("ask") String ask){
        String role = "图书馆管理员";

        PromptTemplate template = PromptTemplate.from("你是一个{{it}}，问题：{{question}}");
        Prompt apply = template.apply(Map.of(
                "it", role,
                "question", ask));
        UserMessage userMessage = apply.toUserMessage();
        ChatResponse response = chatModel.chat(userMessage);
        log.info("[usePromptTemplate]res = {}", JsonUtils.toJson(response));
        return response.aiMessage().text();
    }

}
