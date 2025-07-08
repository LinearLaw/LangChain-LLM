package com.linear.langchain.listener;

import dev.langchain4j.model.chat.listener.ChatModelListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatListener implements ChatModelListener {
    public void onRequest(dev.langchain4j.model.chat.listener.ChatModelRequestContext requestContext) {
        log.info("request: {}", requestContext);
    }

    public void onResponse(dev.langchain4j.model.chat.listener.ChatModelResponseContext responseContext) {
        log.info("response: {}", responseContext);
    }

    public void onError(dev.langchain4j.model.chat.listener.ChatModelErrorContext errorContext) {
        log.error("error: {}", errorContext);
    }
}
