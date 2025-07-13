package com.linear.langchain.config.persistence;

import com.linear.langchain.service.ChatPersistenceAssistant;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 持久化，设置将对话存储到redis
 */
@Configuration
public class PersistenceConfig {

    @Resource
    private RedisChatMemoryStore chatMemoryStore;

    @Bean
    public ChatPersistenceAssistant persistenceAssistant(ChatModel chatModel){
        ChatMemoryProvider chatMemoryProvider = new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(1000)
                        .chatMemoryStore(chatMemoryStore)
                        .build();
            }
        };
        return AiServices.builder(ChatPersistenceAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }

}
