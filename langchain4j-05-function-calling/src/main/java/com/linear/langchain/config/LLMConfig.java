package com.linear.langchain.config;

import com.linear.langchain.service.ChatAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class LLMConfig {

    /**
     * use llm api directly
     */
    @Bean(name = "llama")
    public ChatModel chatModelLlama(){
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("ALI_API_KEY"))
                .modelName("llama-4-maverick-17b-128e-instruct")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .logRequests(true)
                .logResponses(true)
                .timeout(Duration.ofSeconds(60))
                .build();
    }

    /**
     * use llm api by AiServices
     */
    @Bean
    public ChatAssistant chatAssistant(@Qualifier("llama") ChatModel chatModel){
        return AiServices.create(ChatAssistant.class, chatModel);
    }
}
