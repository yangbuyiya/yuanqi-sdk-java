/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.yby6.sdk.session.defaults;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yby6.sdk.IYuanQiApi;
import com.yby6.sdk.common.Constants;
import com.yby6.sdk.domain.yuanqi.ChatChoice;
import com.yby6.sdk.domain.yuanqi.YuanQiCompletionRequest;
import com.yby6.sdk.domain.yuanqi.YuanQiCompletionResponse;
import com.yby6.sdk.session.YuanQiConfiguration;
import com.yby6.sdk.session.YuanQiSession;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 默认的 YuanQi 会话实现YuanQiSession
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
public class DefaultYuanQiSession implements YuanQiSession {


    /**
     * 配置信息
     */
    private final YuanQiConfiguration yuanQiConfiguration;

    /**
     * YuanQi 接口
     */
    private final IYuanQiApi yuanQiApi;
    /**
     * 工厂事件
     */
    private final EventSource.Factory factory;

    public DefaultYuanQiSession(YuanQiConfiguration yuanQiConfiguration) {
        this.yuanQiConfiguration = yuanQiConfiguration;
        this.yuanQiApi = yuanQiConfiguration.getYuanQiApi();
        this.factory = yuanQiConfiguration.createRequestFactory();
    }

    /**
     * 问答模型元器智能体AI
     *
     * @param yuanqiCompletionRequest 请求信息
     * @return 应答结果
     */
    @Override
    public YuanQiCompletionResponse completions(YuanQiCompletionRequest yuanqiCompletionRequest) {
        return this.yuanQiApi.completions(yuanqiCompletionRequest).blockingGet();
    }

    /**
     * 问答模型 & 流式反馈
     *
     * @param yuanqiCompletionRequest 请求信息
     * @param eventSourceListener     实现监听；通过监听的 onEvent 方法接收数据
     * @return 应答结果
     */
    @Override
    public EventSource chatCompletions(YuanQiCompletionRequest yuanqiCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
        return chatCompletions(Constants.NULL, Constants.NULL, yuanqiCompletionRequest, eventSourceListener);
    }


    @Override
    public CompletableFuture<String> chatCompletions(YuanQiCompletionRequest chatCompletionRequest) throws InterruptedException, JsonProcessingException {
        // 用于执行异步任务并获取结果
        CompletableFuture<String> future = new CompletableFuture<>();
        StringBuffer dataBuffer = new StringBuffer();

        chatCompletions(chatCompletionRequest, new EventSourceListener(){
            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                if ("[DONE]".equalsIgnoreCase(data)) {
                    onClosed(eventSource);
                    future.complete(dataBuffer.toString());
                }

                YuanQiCompletionResponse chatCompletionResponse = JSONUtil.toBean(data, YuanQiCompletionResponse.class);
                List<ChatChoice> choices = chatCompletionResponse.getChoices();
                for (ChatChoice chatChoice : choices) {

                    // 应答完成
                    String finishReason = chatChoice.getFinishReason();
                    if ("stop".equalsIgnoreCase(finishReason)) {
                        onClosed(eventSource);
                        return;
                    }

                    // 发送信息
                    try {
                        dataBuffer.append(chatChoice.getMessage().getContent());
                    } catch (Exception e) {
                        future.completeExceptionally(new RuntimeException("Request closed before completion"));
                    }

                }
            }

            @Override
            public void onClosed(EventSource eventSource) {
                future.complete(dataBuffer.toString());
            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response) {
                future.completeExceptionally(new RuntimeException("Request closed before completion"));
            }
        });

        return future;
    }

    /**
     * 问答模型 & 流式反馈
     *
     * @param apiHostByUser           自定义host
     * @param apiKeyByUser            自定义Key
     * @param yuanqiCompletionRequest 请求信息
     * @param eventSourceListener     实现监听；通过监听的 onEvent 方法接收数据
     * @return 应答结果
     */
    @Override
    public EventSource chatCompletions(String apiHostByUser, String apiKeyByUser, YuanQiCompletionRequest yuanqiCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
        // 当前为流式模式，如果为false则抛出异常
        Assert.isFalse(yuanqiCompletionRequest.getStream(), "illegal parameter stream is false!");
        
        // 动态设置 Host、Key，便于用户传递自己的信息
        String apiHost = Constants.NULL.equals(apiHostByUser) ? yuanQiConfiguration.getApiHost() : apiHostByUser;
        String apiKey = Constants.NULL.equals(apiKeyByUser) ? yuanQiConfiguration.getApiKey() : apiKeyByUser;

        // 构建请求信息
        Request request = new Request.Builder()
                // url: https://yuanqi.tencent.com/openapi/v1/agent/chat/completions -
                // 通过 IYuanQiApi 配置的 POST 接口，用这样的方式从统一的地方获取配置信息
                .url(apiHost.concat(IYuanQiApi.v1_chat_completions))
                .addHeader(Constants.AUTHORIZATION, apiKey)
                // 封装请求参数信息
                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), new ObjectMapper()
                        .writeValueAsString(yuanqiCompletionRequest)))
                .build();

        // 返回结果信息；EventSource 对象可以取消应答
        return factory.newEventSource(request, eventSourceListener);
    }
}
