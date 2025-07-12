package com.linear.langchain.controller;

import com.alibaba.dashscope.utils.JsonUtils;
import com.linear.langchain.service.ChatAssistant;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stream")
@Slf4j
public class StreamController {

    /**
     * 1、use Flux and chatModel lower API
     * 2、use lower API only
     * 3、use high API: include Flux and chatModel
     */
    @Resource
    private StreamingChatModel streamingChatModel;

    @Resource
    private ChatAssistant chatAssistant;

    /**
     * http://localhost:8080/stream/chat?ask=历史上徐州发生过哪些著名的战争
     */
    @GetMapping(value = "chat")
    public Flux<String> chat(@RequestParam("ask") String ask){
        log.info("----- streaming chat start -----");
        return Flux.create(emitter -> {
            streamingChatModel.chat(ask, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String s) {
                    emitter.next(s);
                }

                @Override
                public void onCompleteResponse(ChatResponse chatResponse) {
                    emitter.complete();
                }

                @Override
                public void onError(Throwable throwable) {
                    log.error("[StreamController_onError]e = {}", throwable.getStackTrace());
                    emitter.error(throwable);
                }
            });
        });
    }

    // http://localhost:8080/stream/chatWithoutResponse?ask=历史上徐州发生过哪些著名的战争
    @GetMapping("chatWithoutResponse")
    public void chatWithoutResponse(@RequestParam("ask") String ask){
        List<String> box = new ArrayList<>();
        streamingChatModel.chat(ask, new StreamingChatResponseHandler() {
            @Override
            public void onPartialResponse(String s) {
                log.info(s);
                box.add(s);
            }

            @Override
            public void onCompleteResponse(ChatResponse chatResponse) {
                log.info("[chatWithoutResponse_complete]res = {}", JsonUtils.toJson(chatResponse));

                log.info(box.toString().replaceAll(",", ""));
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("[chatWithoutResponse_onError]e = {}", throwable.getStackTrace());
            }
        });
    }

    /**
     * http://localhost:8080/stream/assistant?ask=请续写下面这段：“徐州地方，历代大规模征战五十余次，但是”
     */
    @GetMapping("assistant")
    public Flux<String> useChatAssistant(@RequestParam("ask") String ask){
        log.info("----- use assistant start -----");
        return chatAssistant.chatFlux(ask);
    }


}
