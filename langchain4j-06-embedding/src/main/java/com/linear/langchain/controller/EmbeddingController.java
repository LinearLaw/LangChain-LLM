package com.linear.langchain.controller;

import com.alibaba.dashscope.utils.JsonUtils;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("embedding")
@Slf4j
public class EmbeddingController {

    @Resource
    private EmbeddingModel embeddingModel;


    /**
     * http://localhost:8080/embedding/model?ask=英伟达，英特尔，AMD，高通，微软中国
     */
    @GetMapping("/model")
    public Response<Embedding> embedding(@RequestParam("ask") String ask){
        Response<Embedding> embed = embeddingModel.embed(ask);

        log.info(JsonUtils.toJson(embed));

        return embed;
    }



}
