package com.yby6.sdk.session.defaults;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yby6.sdk.IYuanQiApi;
import com.yby6.sdk.common.Constants;
import com.yby6.sdk.domain.yuanqi.YuanQiCompletionRequest;
import com.yby6.sdk.domain.yuanqi.YuanQiCompletionResponse;
import com.yby6.sdk.session.YuanQiConfiguration;
import com.yby6.sdk.session.YuanQiSession;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

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
     * OpenAI 接口
     */
    private final IYuanQiApi openAiApi;
    /**
     * 工厂事件
     */
    private final EventSource.Factory factory;

    public DefaultYuanQiSession(YuanQiConfiguration yuanQiConfiguration) {
        this.yuanQiConfiguration = yuanQiConfiguration;
        this.openAiApi = yuanQiConfiguration.getOpenAiApi();
        this.factory = yuanQiConfiguration.createRequestFactory();
    }

    /**
     * 问答模型 GPT-3.5/4.0
     *
     * @param yuanqiCompletionRequest 请求信息
     * @return 应答结果
     */
    @Override
    public YuanQiCompletionResponse completions(YuanQiCompletionRequest yuanqiCompletionRequest) {
        return this.openAiApi.completions(yuanqiCompletionRequest).blockingGet();
    }

    /**
     * 问答模型 GPT-3.5/4.0 & 流式反馈
     *
     * @param yuanqiCompletionRequest 请求信息
     * @param eventSourceListener     实现监听；通过监听的 onEvent 方法接收数据
     * @return 应答结果
     */
    @Override
    public EventSource chatCompletions(YuanQiCompletionRequest yuanqiCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
        return chatCompletions(Constants.NULL, Constants.NULL, yuanqiCompletionRequest, eventSourceListener);
    }

    /**
     * 问答模型 GPT-3.5/4.0 & 流式反馈
     *
     * @param apiHostByUser           自定义host
     * @param apiKeyByUser            自定义Key
     * @param yuanqiCompletionRequest 请求信息
     * @param eventSourceListener     实现监听；通过监听的 onEvent 方法接收数据
     * @return 应答结果
     */
    @Override
    public EventSource chatCompletions(String apiHostByUser, String apiKeyByUser, YuanQiCompletionRequest yuanqiCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
        // 核心参数校验；不对用户的传参做更改，只返回错误信息。
        if (!yuanqiCompletionRequest.getStream()) {
            throw new RuntimeException("illegal parameter stream is false!");
        }

        // 动态设置 Host、Key，便于用户传递自己的信息
        String apiHost = Constants.NULL.equals(apiHostByUser) ? yuanQiConfiguration.getApiHost() : apiHostByUser;
        String apiKey = Constants.NULL.equals(apiKeyByUser) ? yuanQiConfiguration.getApiKey() : apiKeyByUser;

        // 构建请求信息
        Request request = new Request.Builder()
                // url: https://yuanqi.tencent.com/openapi/v1/agent/chat/completions -
                // 通过 IYuanQiApi 配置的 POST 接口，用这样的方式从统一的地方获取配置信息
                .url(apiHost.concat(IYuanQiApi.v1_chat_completions))
                .addHeader(Constants.AUTHORIZATION, apiKey)
                // 封装请求参数信息，如果使用了 Fastjson 也可以替换 ObjectMapper 转换对象
                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), new ObjectMapper()
                        .writeValueAsString(yuanqiCompletionRequest)))
                .build();

        // 返回结果信息；EventSource 对象可以取消应答
        return factory.newEventSource(request, eventSourceListener);
    }
}
