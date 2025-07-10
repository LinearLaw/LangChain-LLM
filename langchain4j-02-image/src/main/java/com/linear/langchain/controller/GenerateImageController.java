package com.linear.langchain.controller;


import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("image/generate")
@Slf4j
public class GenerateImageController {

    @Resource
    private WanxImageModel wanxImageModel;

    /**
     * http://localhost:8080/image/generate/create
     * @return
     */
    @GetMapping("/create")
    public String createImage(){
        Response<Image> president = wanxImageModel.generate("请用二次元风格、像素风格绘制亚瑟王的画像");
        log.info(JsonUtils.toJson(president));
        log.info(String.valueOf(president.content().url()));
        return president.content().url().toString();
    }

    /**
     * 文生图模型
     * 通过 ImageSynthesisParam 生成图片
     * 增加更多的描述信息，得到更加准确的图片
     *
     * http://localhost:8080/image/generate/create/useImageParam
     */
    @GetMapping("/create/useImageParam")
    public String createImageLimit(){
        String prompt = "请用二次元风格、像素风格绘制亚瑟王的画像，" +
                "亚瑟王性别女，参考fate/stay night的人物Saber，" +
                "全身特写，环境为室外，电影级光照，场景有草地、山坡，有风吹过。";

        ImageSynthesisParam param = ImageSynthesisParam.builder()
                .apiKey(System.getenv("ALI_API_KEY"))
                .model(ImageSynthesis.Models.WANX_V1)
                .prompt(prompt)
                .style("<watercolor>")
                .n(1)
                .size("1024*1024")
                .build();
        ImageSynthesis imageSynthesis = new ImageSynthesis();
        try {
            ImageSynthesisResult result = imageSynthesis.call(param);
            log.info(String.valueOf(result));
            return JsonUtils.toJson(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
