/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.yby6;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yby6.yuanqi.sdk.common.Constants;
import com.yby6.yuanqi.sdk.domain.yuanqi.*;
import com.yby6.yuanqi.sdk.session.YuanQiConfiguration;
import com.yby6.yuanqi.sdk.session.YuanQiSession;
import com.yby6.yuanqi.sdk.session.defaults.DefaultYuanQiSessionFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class YuanQIAPI {

    private YuanQiSession yuanQiSession;

    @Before
    public void test_OpenAiSessionFactory() {
        // 1. 配置文件
        YuanQiConfiguration yuanQiConfiguration = new YuanQiConfiguration();
        yuanQiConfiguration.setApiHost("https://yuanqi.tencent.com/openapi/");
        yuanQiConfiguration.setApiKey("你的智能体");
        // 2. 会话工厂
        DefaultYuanQiSessionFactory factory = new DefaultYuanQiSessionFactory(yuanQiConfiguration);
        // 3. 开启会话
        this.yuanQiSession = factory.openSession();
        log.info("openAiSession:{}", yuanQiSession);
    }

    /**
     * 返回参数:
     * {
     * "id": "43b88ac7daab69d28e6320ae9ea39dc5",
     * "created": 1717177745,
     * "choices": [
     * {
     * "finish_reason": "stop",
     * "message": {
     * "role": "assistant",
     * "content": "我是隔壁老王，一个无所不能的智能体，分分钟秒掉问题！",
     * "steps": [
     * {
     * "role": "assistant",
     * "content": "我是隔壁老王，一个无所不能的智能体，分分钟秒掉问题！",
     * "usage": {
     * "prompt_tokens": 252,
     * "completion_tokens": 15,
     * "total_tokens": 267
     * },
     * "time_cost": 1718
     * }
     * ]
     * }
     * }
     * ],
     * "assistant_id": "mmbnqMnLdYz0",
     * "usage": {
     * "prompt_tokens": 252,
     * "completion_tokens": 15,
     * "total_tokens": 267
     * }
     * }
     */
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
     * 测试聊天完成字符串
     */
    @Test
    public void test_chat_completions_string() {
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
        String yuanQiCompletionResponse = yuanQiSession.completionsString(chatCompletion);
        // 3. 解析结果
        log.info("消息: {}", yuanQiCompletionResponse);
    }

    /**
     * 流试返回参数:
     * {
     * "id": "82956d810a0ff9b413c8bf924c2190c3",
     * "created": 1717177832,
     * "choices": [
     * {
     * "delta": {
     * "role": "assistant",
     * "content": "我是",
     * "time_cost": 0
     * }
     * }
     * ],
     * "assistant_id": "mmbnqMnLdYz0",
     * "usage": {
     * "prompt_tokens": 0,
     * "completion_tokens": 0,
     * "total_tokens": 0
     * }
     * }
     */
    @Test
    public void test_chat_completions_stream() throws JsonProcessingException, InterruptedException {
        // 1. 创建参数
        YuanQiCompletionRequest chatCompletion = YuanQiCompletionRequest
                .builder()
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content(
                        Collections.singletonList(
                                MessageContent.builder().type(Constants.Type.TEXT)
                                        .text("老王,你可以给我解析这个URL吗?  https://yby.com").build()
                        )
                ).build()))
                .userId("rodneyxiong")
                .assistantId("mmbnqMnLdYz0")
                .stream(true)
                .build();
        // 2. 发起请求
        EventSource eventSource = yuanQiSession.chatCompletions(chatCompletion, new EventSourceListener() {
            @Override
            public void onEvent(@NotNull EventSource eventSource, String id, String type, @NotNull String data) {
                try {
                    if (data.equals("[DONE]")) {
                        return;
                    }

                    YuanQiCompletionResponse response = JSONUtil.toBean(data, YuanQiCompletionResponse.class);

                    List<ChatChoice> choices = response.getChoices();
                    for (ChatChoice chatChoice : choices) {
                        final ChatChoice.MessageDTO.StepsDTO delta = chatChoice.getDelta();

                        // 应答完成
                        String finishReason = chatChoice.getFinishReason();
                        if (StrUtil.isNotBlank(finishReason) && "stop".equals(finishReason)) {
                            break;
                        }

                        // 发送信息
                        try {
                            if (null != delta) {
                                log.info(delta.getContent());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response) {
                log.error("错误: {}, {}", response.code(), t.getMessage());
            }
        });
        // 等待
        new CountDownLatch(1).await();
    }
    
    /**
     * 多模态测试
     */
    @Test
    public void test_chat_completions_many_stream() throws JsonProcessingException, InterruptedException {



        // 机器人
        Message message = Message.builder()
                .role(Constants.Role.USER)
                .content(
                        
                        List.of(
                                MessageContent.builder()
                                        .type(Constants.Type.TEXT)
                                        .text("把这张图换成沙漠背景")
                                        .build(),
                                
                                MessageContent.builder()
                                        .type(Constants.Type.IMG)
                                        .fileUrl(
                                                FileUrl.builder()
                                                        .type("image")
                                                        .url("https://cdn.yuanqi.tencent.com/hunyuan_open/default/c6df61a5ce7ec829f31108acd9a5fb6d.png?sign=1718090186-1718090186-0-a56dc47eb102fb044b96e985b7a8acaa8680d11f1bbd2d4f44469aa751016f2f")
                                                        .build()
                                        )
                                        .build()
                        )
                
                )
                .build();
        
        
        // 1. 创建参数
        YuanQiCompletionRequest chatCompletion = YuanQiCompletionRequest
                .builder()
                .messages(Collections.singletonList(
                        
                        message
                        
                        ))
                .userId("rodneyxiong")
                .assistantId("mmbnqMnLdYz0")
                .stream(false)
                .build();
        // 2. 发起请求
        String yuanQiCompletionResponse = yuanQiSession.completionsString(chatCompletion);
        // 3. 解析结果
        log.info("消息: {}", yuanQiCompletionResponse);
    }
}
