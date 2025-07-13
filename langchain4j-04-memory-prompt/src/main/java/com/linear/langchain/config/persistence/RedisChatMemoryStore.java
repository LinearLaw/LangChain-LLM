package com.linear.langchain.config.persistence;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RedisChatMemoryStore implements ChatMemoryStore {

    public static String PRESET_CHAT_MEMORY = "CHAT_MEMORY:";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String key = PRESET_CHAT_MEMORY + memoryId.toString();
        String value = redisTemplate.opsForValue().get(key);
        log.info("[get]key={}, value={}", key, value);
        return ChatMessageDeserializer.messagesFromJson(value);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        String key = PRESET_CHAT_MEMORY + memoryId.toString();
        log.info("[update]key={}, value={}", key, list);
        redisTemplate.opsForValue()
                .set(key, ChatMessageSerializer.messagesToJson(list));
    }

    @Override
    public void deleteMessages(Object memoryId) {
        String key = PRESET_CHAT_MEMORY + memoryId.toString();
        log.info("[delete]key={}", key);
        redisTemplate.delete(key);
    }
}
