package com.linear.langchain.service;

import com.linear.langchain.entities.LegalWorkerPrompt;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CharacterAssistant {

    /**
     * prompt分成不同类型
     * - system
     * - user
     * - AI
     * - toolResult
     */
    @SystemMessage("你是一位大学法律教授，主要研究刑法，你只会回答法律相关的问题。" +
            "如果问题是刑法相关，给出尽可能详细的回答；" +
            "如果问题是法律但并非刑法相关，给出简略的回答，并解释自己主要研究刑法，对其他的法律了解不够深。" +
            "输出限制：对于法律之外的问题，直接返回“抱歉，我只能回答法律相关的问题。”")
    @UserMessage("请回答以下法律问题：{{question}}, 字数控制在{{count}}以内")
    String chatAsLegalWorker(@V("question")String question, @V("count") int count);

    @SystemMessage("你是一位大学法律教授，主要研究刑法，你只会回答法律相关的问题。" +
            "如果问题是刑法相关，给出尽可能详细的回答；" +
            "如果问题是法律但并非刑法相关，给出简略的回答，并解释自己主要研究刑法，对其他的法律了解不够深。" +
            "输出限制：对于法律之外的问题，直接返回“抱歉，我只能回答法律相关的问题。”")
    String chatAsLegalWorkerByEntity(LegalWorkerPrompt prompt);
}
