package com.linear.langchain.config;

import com.linear.langchain.service.FunctionAssistant;
import com.linear.langchain.service.InvoiceHandler;
import com.linear.langchain.service.WeatherHandler;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FunctionCallingConfig {

    @Bean("lower")
    public FunctionAssistant useLower(ChatModel chatModel){
        return AiServices.builder(FunctionAssistant.class)
                .chatModel(chatModel)
                .tools(List.of(new InvoiceHandler(), new WeatherHandler()))
                .build();
    }

}
