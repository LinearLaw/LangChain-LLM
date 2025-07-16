package com.linear.langchain.config;

import com.google.protobuf.Message;
import com.linear.langchain.service.ChatAssistant;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
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
     * 使用内存作为向量存储
     */
    @Bean
    public InMemoryEmbeddingStore<TextSegment> inMemoryEmbeddingStore(){
        return new InMemoryEmbeddingStore<>();
    }

    @Bean
    public ChatAssistant chatAssistant(ChatModel chatModel,
                                       EmbeddingStore<TextSegment> embeddingStore){
        return AiServices.builder(ChatAssistant.class)
                .chatModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(100))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();
    }

}
