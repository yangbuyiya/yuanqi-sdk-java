package com.yby6.sdk;


import com.yby6.sdk.domain.yuanqi.YuanQiCompletionRequest;
import com.yby6.sdk.domain.yuanqi.YuanQiCompletionResponse;
import io.reactivex.Single;
import retrofit2.http.*;

/**
 * 以 YuanQi 官网 API 模型，定义接口。
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
public interface IYuanQiApi {

    // https://yuanqi.tencent.com/openapi/v1/agent/chat/completions
    String v1_chat_completions = "v1/agent/chat/completions";

    /**
     * 问答智能体
     *
     * @param YuanQiCompletionRequest 请求信息
     * @return 应答结果
     */
    @POST(v1_chat_completions)
    Single<YuanQiCompletionResponse> completions(@Body YuanQiCompletionRequest YuanQiCompletionRequest);


}
