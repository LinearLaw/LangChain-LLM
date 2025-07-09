package com.linear.langchain.controller;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("image")
public class ReadImageController {

    @jakarta.annotation.Resource(name = "qwen-vl-max")
    private ChatModel qwenVlMax;

    @Value("classpath:static/MeiTuan-03690-20250709.png")
    private Resource imgResource;

    /**
     * 利用LLM的多模态，分析图片，输出结果
     * http://localhost:8080/image/read
     */
    @GetMapping("/read")
    public String readImage(){
        String result = "读取图片MeiTuan-03690-20250709.png";

        try {
            // 转base64编码
            byte[] byteArray = imgResource.getContentAsByteArray();
            String encode = Base64.getEncoder().encodeToString(byteArray);

            // 定义message，定义图片 + 提示词
            UserMessage from = UserMessage.from(
                    TextContent.from("从下面图片中，获取来源的网站名称，获取股价走势和7月9日的股价，并对近期的股票走势进行分析"),
                    ImageContent.from(encode, "image/jpg")
            );

            // call llm
            ChatResponse response = qwenVlMax.chat(from);
            result = response.aiMessage().text();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /**
         result
         ### 1. 来源的网站名称 从图片中的水印可以看出，该图表来源于“东方财富网”。
         ### 2. 股价走势和7月9日的股价
         - **股价走势**：图表显示的是美团-W（股票代码：03690）在一段时间内的股价走势。整体来看，股价在经历了一段上升趋势后，近期呈现震荡下行的趋势。
         - **7月9日的股价**：根据图表，7月9日的收盘价为约118.658港元。
         ### 3. 近期的股票走势分析
         #### 3.1 K线图分析
         - **短期趋势**：从K线图上看，近期股价呈现出明显的下跌趋势，尤其是最近几个交易日，股价连续下跌，显示出较强的卖压。
         - **中期趋势**：中期来看（如20日均线），股价也在逐渐走低，表明中期趋势也偏向于下行。
         #### 3.2 均线分析
         - **短期均线（5日、10日均线）**：短期均线向下运行，且5日均线在10日均线下方，表明短期趋势偏空。
         - **中期均线（20日均线）**：20日均线也呈现下行趋势，进一步确认了中期趋势的疲软。
         #### 3.3 成交量分析
         - **成交量**：近期成交量相对稳定，没有出现急剧放大的情况，表明市场情绪较为谨慎，投资者观望情绪较浓。
         #### 3.4 技术指标分析
         - **RSI（相对强弱指数）**：RSI值分别为27.998、32.299、38.441，均低于50，且接近超卖区域（通常认为RSI低于30为超卖），表明市场短期内可能有反弹的需求。
         - **MACD（移动平均收敛散度）**：MACD指标显示DIFF线和DEA线在零轴下方运行，且DIFF线在DEA线下方，形成死叉，表明当前市场处于空头市场。
         ### 4. 综合分析 综合以上分析，可以得出以下结论：
         - **短期趋势**：短期内股价继续下行的可能性较大，但RSI指标接近超卖区域，可能会有短期反弹。
         - **中期趋势**：中期趋势偏空，投资者应保持谨慎态度。
         - **操作建议**：短期内可关注是否有反弹机会，但整体趋势偏空，不宜盲目追高，可等待市场明朗后再做决策。
         ### 5. 结论 美团-W（03690）近期股价走势偏空，7月9日收盘价约为118.658港元。短期内可能会有反弹机会，但整体趋势仍需谨慎对待。投资者应密切关注市场动态，合理控制风险。
         */
        return result;
    }
}
