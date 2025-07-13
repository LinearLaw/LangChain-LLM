package com.linear.langchain.config;

import com.linear.langchain.service.ChatMemoryAssistant;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryConfig {
    /**
     * message window config
     */
    @Bean("chatMessageWindow")
    public ChatMemoryAssistant chatByMessageWindow(ChatModel chatModel){
        return AiServices.builder(ChatMemoryAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(100))
                .build();
    }

    @Bean("chatTokenWindow")
    public ChatMemoryAssistant chatByTokenWindow(ChatModel chatModel){
        // OpenAiTokenCountEstimator 默认提供了 gpt-4 的token计费工具
        // 从构造函数可以看到
        OpenAiTokenCountEstimator estimator = new OpenAiTokenCountEstimator("gpt-4");

        return AiServices.builder(ChatMemoryAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(memoryId -> TokenWindowChatMemory.withMaxTokens(
                        1000,
                        estimator
                ))
                .build();
    }
}
