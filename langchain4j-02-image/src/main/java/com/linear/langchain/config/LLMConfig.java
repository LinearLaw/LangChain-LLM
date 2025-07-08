package com.linear.langchain.config;

import com.linear.langchain.listener.ChatListener;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class LLMConfig {

    @Bean(name = "qwen")
    public ChatModel chatModelQwen(){
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("ALI_API_KEY"))
                .modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .logRequests(true)
                .logResponses(true)
                .maxRetries(2)
                .timeout(Duration.ofSeconds(60))
                .listeners(List.of(new ChatListener()))
                .build();
    }

    @Bean(name = "deepseek")
    public ChatModel chatModelDeepSeek(){
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("ALI_API_KEY"))
                .modelName("deepseek-r1")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .logRequests(true)
                .logResponses(true)
                .maxRetries(2)
                .timeout(Duration.ofSeconds(60))
                .build();
    }
}
