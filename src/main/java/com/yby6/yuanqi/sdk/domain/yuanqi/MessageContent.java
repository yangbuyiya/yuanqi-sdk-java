/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.yby6.yuanqi.sdk.domain.yuanqi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yby6.yuanqi.sdk.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 消息内容
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class MessageContent implements Serializable {

    /**
     * 问答类型
     * text
     */
    private String type;

    /**
     * 问题
     */
    private String text;
    
    /**
     * 当type为file_url时使用，表示具体的文件内容
     */
    private FileUrl fileUrl;

    private MessageContent(Builder builder) {
        this.type = builder.type;
        this.text = builder.text;
        this.fileUrl = builder.fileUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 建造者模式
     */
    public static final class Builder {

        private String type;

        /**
         * 问题
         */
        private String text;
        
        /**
         * 当type为file_url时使用，表示具体的文件内容
         */
        private FileUrl fileUrl;

        public Builder() {
        }

        public Builder type(Constants.Type type) {
            this.type = type.getCode();
            return this;
        }

        public Builder text(String type) {
            this.text = type;
            return this;
        }
        
        public Builder fileUrl(FileUrl fileUrl) {
            this.fileUrl = fileUrl;
            return this;
        }

        public MessageContent build() {
            return new MessageContent(this);
        }
    }
}
