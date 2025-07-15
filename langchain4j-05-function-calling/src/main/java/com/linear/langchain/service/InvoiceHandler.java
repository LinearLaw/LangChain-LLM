package com.linear.langchain.service;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvoiceHandler {

    @Tool("根据用户提交的开票信息进行开发票")
    public String handle(@P("公司名称") String companyName,
                         @P("税号") String dutyNumber,
                         @P("金额") String amount){
        return "开发票成功：公司名称=" + companyName + "，税号=" + dutyNumber + "，金额=" + amount;
    }
}
