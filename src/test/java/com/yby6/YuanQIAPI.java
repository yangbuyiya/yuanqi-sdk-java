package com.yby6;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yby6.sdk.common.Constants;
import com.yby6.sdk.domain.yuanqi.Message;
import com.yby6.sdk.domain.yuanqi.MessageContent;
import com.yby6.sdk.domain.yuanqi.YuanQiCompletionRequest;
import com.yby6.sdk.domain.yuanqi.YuanQiCompletionResponse;
import com.yby6.sdk.session.YuanQiConfiguration;
import com.yby6.sdk.session.YuanQiSession;
import com.yby6.sdk.session.defaults.DefaultYuanQiSessionFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class YuanQIAPI {

    private YuanQiSession yuanQiSession;

    @Before
    public void test_OpenAiSessionFactory() {
        // 1. 配置文件
        YuanQiConfiguration yuanQiConfiguration = new YuanQiConfiguration();
        yuanQiConfiguration.setApiHost("https://yuanqi.tencent.com/openapi/");
        yuanQiConfiguration.setApiKey("Bearer zajBIuOU0XC0xb8kbP0SXDlR0aoEUESV");
        // 2. 会话工厂
        DefaultYuanQiSessionFactory factory = new DefaultYuanQiSessionFactory(yuanQiConfiguration);
        // 3. 开启会话
        this.yuanQiSession = factory.openSession();
        log.info("openAiSession:{}", yuanQiSession);
    }

    @Test
    public void test_chat_completions() {
        // 1. 创建参数
        YuanQiCompletionRequest chatCompletion = YuanQiCompletionRequest
                .builder()
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content(
                        Collections.singletonList(
                                MessageContent.builder().type(Constants.Type.TEXT)
                                        .text("你是谁啊?").build()
                        )
                ).build()))
                .userId("rodneyxiong")
                .assistantId("mmbnqMnLdYz0")
                .stream(false)
                .build();
        // 2. 发起请求
        YuanQiCompletionResponse yuanQiCompletionResponse = yuanQiSession.completions(chatCompletion);
        // 3. 解析结果
        yuanQiCompletionResponse.getChoices().forEach(e -> {
            log.info("测试结果：{}", e.getMessage());
            log.info("消息: {}", e.getMessage().getContent());
        });
    }


    /**
     * 此对话模型 3.5 接近于官网体验 & 流式应答
     */
//    @Test
//    public void test_chat_completions_stream() throws JsonProcessingException, InterruptedException {
//        // 1. 创建参数
//        ChatCompletionRequest chatCompletion = ChatCompletionRequest
//                .builder()
//                .stream(true)
//                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build()))
//                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
//                .build();
//        // 2. 发起请求
//        EventSource eventSource = openAiSession.chatCompletions(chatCompletion, new EventSourceListener() {
//            @Override
//            public void onEvent(@NotNull EventSource eventSource, String id, String type, @NotNull String data) {
//                log.info("测试结果：{}", data);
//            }
//        });
//
//        // 等待
//        new CountDownLatch(1).await();
//    }

}
