package com.yby6.sdk.session;


/**
 * YuanQi 会话工厂
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
public interface YuanQiSessionFactory {

    /**
     * 开启会话
     *
     * @return {@link YuanQiSession}
     */
    YuanQiSession openSession();

}
