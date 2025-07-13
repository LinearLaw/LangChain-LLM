package com.linear.langchain.config.persistence;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisChatMemoryStore implements ChatMemoryStore {

    public static String PRESET_CHAT_MEMORY = "CHAT_MEMORY:";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String value = redisTemplate.opsForValue().get(PRESET_CHAT_MEMORY + memoryId);
        return ChatMessageDeserializer.messagesFromJson(value);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        String key = PRESET_CHAT_MEMORY + memoryId;
        redisTemplate.opsForValue()
                .set(key, ChatMessageSerializer.messagesToJson(list));
    }

    @Override
    public void deleteMessages(Object memoryId) {
        String key = PRESET_CHAT_MEMORY + memoryId;
        redisTemplate.delete(key);
    }
}
