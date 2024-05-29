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


    YuanQiCompletionResponse completions(YuanQiCompletionRequest yuanqiCompletionRequest);

    EventSource chatCompletions(YuanQiCompletionRequest yuanqiCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;

    CompletableFuture<String> chatCompletions(YuanQiCompletionRequest chatCompletionRequest) throws InterruptedException, JsonProcessingException;

    EventSource chatCompletions(String apiHostByUser, String apiKeyByUser, YuanQiCompletionRequest yuanqiCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;


}
