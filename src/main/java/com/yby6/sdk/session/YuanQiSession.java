package com.yby6.sdk.session;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.yby6.sdk.domain.yuanqi.YuanQiCompletionRequest;
import com.yby6.sdk.domain.yuanqi.YuanQiCompletionResponse;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import java.util.concurrent.CompletableFuture;

/**
 * YuanQi 会话接口
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
public interface YuanQiSession {


    /**
     * 问答模型 GPT-3.5/4.0
     *
     * @param yuanqiCompletionRequest 请求信息
     * @return 应答结果
     */
    YuanQiCompletionResponse completions(YuanQiCompletionRequest yuanqiCompletionRequest);

    /**
     * 问答模型 GPT-3.5/4.0 & 流式反馈
     *
     * @param yuanqiCompletionRequest 请求信息
     * @param eventSourceListener   实现监听；通过监听的 onEvent 方法接收数据
     * @return 应答结果
     */
    EventSource chatCompletions(YuanQiCompletionRequest yuanqiCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;

    /**
     * 问答模型 GPT-3.5/4.0 & 流式反馈
     *
     * @param apiHostByUser         自定义host
     * @param apiKeyByUser          自定义Key
     * @param yuanqiCompletionRequest 请求信息
     * @param eventSourceListener   实现监听；通过监听的 onEvent 方法接收数据
     * @return 应答结果
     */
    EventSource chatCompletions(String apiHostByUser, String apiKeyByUser, YuanQiCompletionRequest yuanqiCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;


}
