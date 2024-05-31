/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.yby6.sdk.session.defaults;

import com.yby6.sdk.IYuanQiApi;
import com.yby6.sdk.interceptor.YuanQiInterceptor;
import com.yby6.sdk.session.YuanQiConfiguration;
import com.yby6.sdk.session.YuanQiSession;
import com.yby6.sdk.session.YuanQiSessionFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(DefaultYuanQiSessionFactory.class);

    private final YuanQiConfiguration yuanQiConfiguration;

    public DefaultYuanQiSessionFactory(YuanQiConfiguration yuanQiConfiguration) {
        this.yuanQiConfiguration = yuanQiConfiguration;
    }

    static {
        // YuanQi-SDK-JAVA
        log.info("\n" +
                " __   __                          ___       _              ___     ___    _  __               _    ___   __   __   ___   \n" +
                " \\ \\ / /  _  _    __ _    _ _    / _ \\     (_)     ___    / __|   |   \\  | |/ /    ___     _ | |  /   \\  \\ \\ / /  /   \\  \n" +
                "  \\ V /  | +| |  / _` |  | ' \\  | (_) |    | |    |___|   \\__ \\   | |) | | ' <    |___|   | || |  | - |   \\ V /   | - |  \n" +
                "  _|_|_   \\_,_|  \\__,_|  |_||_|  \\__\\_\\   _|_|_   _____   |___/   |___/  |_|\\_\\   _____   _\\__/   |_|_|   _\\_/_   |_|_|  \n" +
                "_| \"\"\" |_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|     |_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|     |_|\"\"\"\"\"|_|\"\"\"\"\"|_| \"\"\"\"|_|\"\"\"\"\"| \n" +
                "\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-' \n");
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
        httpLoggingInterceptor.setLevel(yuanQiConfiguration.getLevel());

        // 2. 开启 Http 客户端
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new YuanQiInterceptor(yuanQiConfiguration.getApiKey())) // 设置 apikey
                .connectTimeout(450, TimeUnit.SECONDS)
                .writeTimeout(450, TimeUnit.SECONDS)
                .readTimeout(450, TimeUnit.SECONDS)
                .build();

        // 3. 创建 API 服务
        IYuanQiApi yuanQiApi = new Retrofit.Builder()
                .baseUrl(yuanQiConfiguration.getApiHost())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(IYuanQiApi.class);
        // 注入配置
        yuanQiConfiguration.setYuanQiApi(yuanQiApi);
        yuanQiConfiguration.setOkHttpClient(okHttpClient);
        return new DefaultYuanQiSession(yuanQiConfiguration);
    }

}
