package com.linear.langchain.controller;


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

    public String createImageLimit(){
        return "";
    }

}
