package com.linear.langchain.entities;

import dev.langchain4j.model.input.structured.StructuredPrompt;
import lombok.Data;

@Data
@StructuredPrompt("根据中国{{legal}}法律，请回答以下法律问题：{{question}}, 字数控制在{{count}}以内")
public class LegalWorkerPrompt {
    private String legal = "刑法";
    private String question;
    private int count;
}
