/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.yby6.yuanqi.sdk.session;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.yby6.yuanqi.sdk.domain.yuanqi.YuanQiCompletionRequest;
import com.yby6.yuanqi.sdk.domain.yuanqi.YuanQiCompletionResponse;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import java.util.concurrent.CompletableFuture;

/**
 * 元气会话
 * YuanQi 会话接口
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
public interface YuanQiSession {
    
    
    /**
     * 简单问答
     *
     * @param yuanqiCompletionRequest 元气完成请求
     * @return {@link YuanQiCompletionResponse}
     */
    YuanQiCompletionResponse completions(YuanQiCompletionRequest yuanqiCompletionRequest);

    /**
     * 简单问答返回String
     *
     * @param yuanqiCompletionRequest 元气完成请求
     * @return {@code String}
     */
    String completionsString(YuanQiCompletionRequest yuanqiCompletionRequest);

    /**
     * 简单问答 - 流式
     *
     * @param yuanqiCompletionRequest 元气完成请求
     * @param eventSourceListener     事件源侦听器
     * @return {@link EventSource}
     * @throws JsonProcessingException json处理异常
     */
    EventSource chatCompletions(YuanQiCompletionRequest yuanqiCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;

    /**
     * 简单问答 - 流式 CompletableFuture
     *
     * @param chatCompletionRequest 聊天完成请求
     * @return {@code CompletableFuture<String>}
     * @throws InterruptedException    中断异常
     * @throws JsonProcessingException json处理异常
     */
    CompletableFuture<String> chatCompletions(YuanQiCompletionRequest chatCompletionRequest) throws InterruptedException, JsonProcessingException;
    
    /**
     * 简单问答 - 流式 - 自定义api
     *
     * @param apiHostByUser           用户提供api主机
     * @param apiKeyByUser            用户提供api密钥
     * @param yuanqiCompletionRequest 元气完成请求
     * @param eventSourceListener     事件源侦听器
     * @return {@link EventSource}
     * @throws JsonProcessingException json处理异常
     */
    EventSource chatCompletions(String apiHostByUser, String apiKeyByUser, YuanQiCompletionRequest yuanqiCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;


}
