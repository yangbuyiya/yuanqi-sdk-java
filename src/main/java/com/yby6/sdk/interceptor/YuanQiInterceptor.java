package com.yby6.sdk.interceptor;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import com.yby6.sdk.common.Constants;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;


import java.io.IOException;

/**
 * 自定义 YuanQI 拦截器
 * 在 okhttp3 请求的时候拦截
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
public class YuanQiInterceptor implements Interceptor {


    /**
     * YuanQI apiKey 需要在官网申请
     */
    private final String apiKeyBySystem;

    /**
     * 访问授权接口的认证 Token
     */

    public YuanQiInterceptor(String apiKeyBySystem) {
        this.apiKeyBySystem = apiKeyBySystem;
    }

    /**
     * 拦截okhttp请求
     *
     * @param chain 链
     * @return 是否继续执行
     */
    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 1. 获取原始 Request
        Request original = chain.request();

        // 2. 读取 apiKey；优先使用自己传递的 apiKey
        String apiKeyByUser = original.header("apiKey");
        String apiKey = null == apiKeyByUser || Constants.NULL.equals(apiKeyByUser) ? apiKeyBySystem : apiKeyByUser;

        // 3. 构建 Request
        Request request = original.newBuilder()
                .url(original.url())
                .header(Header.AUTHORIZATION.getValue(), apiKey)
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .header(Constants.X_SOURCE,  Constants.OPENAPI)
                .method(original.method(), original.body())
                .build();

        // 4. 返回执行结果
        return chain.proceed(request);
    }

}
