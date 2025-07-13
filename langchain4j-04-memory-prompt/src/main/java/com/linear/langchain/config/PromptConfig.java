package com.linear.langchain.config;

import com.linear.langchain.service.CharacterAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PromptConfig {

    @Bean
    public CharacterAssistant characterAssistant(ChatModel chatModel){
        return AiServices.create(CharacterAssistant.class, chatModel);
    }

}
