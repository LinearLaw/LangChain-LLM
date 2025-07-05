package com.linear.langchain.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig {

    @Bean(name = "qwen")
    public ChatModel chatModelQwen(){
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("ALI_API_KEY"))
                .modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    @Bean(name = "deepseek")
    public ChatModel chatModelDeepSeek(){
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("ALI_API_KEY"))
                .modelName("deepseek-r1")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

}
