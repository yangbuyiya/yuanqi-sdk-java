/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */
package com.yby6.yuanqi.sdk.session;

/**
 * Api密钥提供程序 Authorization Key Provider
 * <p>
 *     如果你需要在程序当中更新API密钥，可以使用{@link ApiKeyProvider#setApiKey(String)}方法
 *     如果你需要获取API密钥，可以使用{@link ApiKeyProvider#getApiKey()}方法
 *     请注意，这个类是线程安全的
 * </p>
 *
 * @author Yang Shuai
 * Create By 2024/06/17
 */
public class ApiKeyProvider {
    private static volatile String apiKey;

    public static String getApiKey() {
        return apiKey;
    }

    public static void setApiKey(String newApiKey) {
        apiKey = newApiKey;
    }
}
