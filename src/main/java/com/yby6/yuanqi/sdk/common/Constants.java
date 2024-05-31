/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.yby6.yuanqi.sdk.common;


import lombok.Getter;

/**
 * 常量 通用类
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
public class Constants {
    /**
     * 空
     */
    public final static String NULL = "NULL";

    /**
     * 请求头 openapi
     */
    public final static String X_SOURCE = "X-Source";
    /**
     * openapi
     */
    public final static String OPENAPI = "openapi";
    /**
     * 鉴权
     */
    public final static String AUTHORIZATION = "Authorization";
    /**
     * token前缀
     */
    public final static String BEARER = "Bearer ";

    /**
     * 角色
     * 官网支持的请求角色类型；system、user、assistant
     * <a href="https://platform.openai.com/docs/guides/chat/introduction">...</a>
     */
    @Getter
    public enum Role {

        /**
         * 系统
         */
        SYSTEM("system"),
        /**
         * 使用者
         */
        USER("user"),
        /**
         * 助理
         */
        ASSISTANT("assistant"),
        ;

        /**
         * 密码
         */
        private final String code;

        /**
         * 角色
         *
         * @param code 密码
         */
        Role(String code) {
            this.code = code;
        }

    }

    /**
     * 问答类型
     */
    @Getter
    public enum Type {

        /**
         * 默认文本问答类型
         */
        TEXT("text")
        ;

        /**
         * 密码
         */
        private final String code;

        /**
         * 角色
         *
         * @param code 密码
         */
        Type(String code) {
            this.code = code;
        }

    }

}
