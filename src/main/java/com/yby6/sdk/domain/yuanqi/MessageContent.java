package com.yby6.sdk.domain.yuanqi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yby6.sdk.common.Constants;
import lombok.Data;

import java.io.Serializable;


/**
 * 消息内容
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    private MessageContent(Builder builder) {
        this.type = builder.type;
        this.text = builder.text;
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

        public MessageContent build() {
            return new MessageContent(this);
        }
    }
}
