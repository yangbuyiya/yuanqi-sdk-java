package com.yby6.sdk.session.defaults;

import com.yby6.sdk.IYuanQiApi;
import com.yby6.sdk.interceptor.YuanQiInterceptor;
import com.yby6.sdk.session.YuanQiConfiguration;
import com.yby6.sdk.session.YuanQiSession;
import com.yby6.sdk.session.YuanQiSessionFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;


/**
 * 默认的实现 OpenAi API Factory 会话工厂
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
public class DefaultYuanQiSessionFactory implements YuanQiSessionFactory {

    private final YuanQiConfiguration yuanQiConfiguration;

    public DefaultYuanQiSessionFactory(YuanQiConfiguration yuanQiConfiguration) {
        this.yuanQiConfiguration = yuanQiConfiguration;
    }

    /**
     * 初始化请求配置
     *
     * @return {@link YuanQiSession}
     */
    @Override
    public YuanQiSession openSession() {
        // 1. 日志配置
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        // 2. 开启 Http 客户端
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new YuanQiInterceptor(yuanQiConfiguration.getApiKey())) // 设置 apikey
                .connectTimeout(450, TimeUnit.SECONDS)
                .writeTimeout(450, TimeUnit.SECONDS)
                .readTimeout(450, TimeUnit.SECONDS)
                //.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 21284)))
                .build();

        // 3. 创建 API 服务
        IYuanQiApi openAiApi = new Retrofit.Builder()
                .baseUrl(yuanQiConfiguration.getApiHost())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(IYuanQiApi.class);
        // 注入配置
        yuanQiConfiguration.setOpenAiApi(openAiApi);
        yuanQiConfiguration.setOkHttpClient(okHttpClient);
        return new DefaultYuanQiSession(yuanQiConfiguration);
    }

}
