package com.yby6.sdk.session;

import com.yby6.sdk.IYuanQiApi;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;
import org.jetbrains.annotations.NotNull;


/**
 * 配置信息
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
@Getter
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YuanQiConfiguration {

    @Setter
    private IYuanQiApi openAiApi;

    @Getter
    @Setter
    private OkHttpClient okHttpClient;

    @Getter
    @NotNull
    private String apiKey;

    @Getter
    private String apiHost;


    public EventSource.Factory createRequestFactory() {
        return EventSources.createFactory(okHttpClient);
    }

}
