package com.linear.langchain.controller;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

@RestController
@RequestMapping("qdrant")
@Slf4j
public class QdrantController {

    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private QdrantClient qdrantClient;

    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    public static String COLLECTION_NAME = "test-qdrant";

    /**
     * 创建collection
     * http://localhost:8080/embedding/collection/create
     */
    @GetMapping("/collection/add")
    public void createCollection(){
        Collections.VectorParams build = Collections.VectorParams.newBuilder()
                .setDistance(Collections.Distance.Cosine)
                .setSize(1024)
                .build();
        qdrantClient.createCollectionAsync(
                "test-qdrant",
                build
        );
    }

    /**
     * 往collection里添加向量
     * http://localhost:8080/embedding/collection/add?ask=英伟达（NVIDIA）是一家成立于1993年的美国跨国科技公司，总部位于加利福尼亚州圣克拉拉市。 该公司主要专注于计算图形、人工智能（AI）和高性能计算领域的产品和服务。其创始人包括黄仁勋、克里斯·马拉科夫斯基和柯蒂斯·普里姆。
     */
    @GetMapping("/collection/add")
    public String add(@RequestParam("ask") String ask){
        TextSegment segment = TextSegment.from(ask);
        segment.metadata().put("author", "linear");

        Embedding content = embeddingModel.embed(segment).content();
        return embeddingStore.add(content, segment);
    }

    /**
     * 搜索
     * http://localhost:8080/embedding/collection/query?ask=英伟达是什么
     */
    @GetMapping("/collection/query")
    public EmbeddingSearchResult<TextSegment> query(@RequestParam("ask") String ask){
        Embedding queryEmbedding = embeddingModel.embed(ask).content();
        EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(3)
                .build();

        EmbeddingSearchResult<TextSegment> result = embeddingStore.search(request);
        return result;
    }

    /**
     * 条件搜索，限定条件后，在向量内进行搜索
     */
    @GetMapping("/collection/query/filter")
    public EmbeddingSearchResult<TextSegment> queryByFilter(@RequestParam("ask") String ask,
                              @RequestParam("author") String linear){
        Embedding content = embeddingModel.embed(ask).content();
        EmbeddingSearchRequest req = EmbeddingSearchRequest.builder()
                .queryEmbedding(content)
                .filter(metadataKey("author").isEqualTo("linear"))
                .maxResults(1)
                .build();

        EmbeddingSearchResult<TextSegment> result = embeddingStore.search(req);

        return result;
    }
}
