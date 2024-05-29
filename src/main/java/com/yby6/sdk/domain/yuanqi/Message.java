package com.yby6.sdk.domain.yuanqi;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.yby6.sdk.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 信息描述
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    /**
     * 角色
     */
    private String role;

    /**
     * 问答体
     */
    private List<MessageContent> content;

    private Message(Builder builder) {
        this.role = builder.role;
        this.content = builder.content;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 建造者模式
     */
    public static final class Builder {

        private String role;
        private List<MessageContent> content;

        public Builder() {
        }

        public Builder role(Constants.Role role) {
            this.role = role.getCode();
            return this;
        }

        public Builder content(List<MessageContent> content) {
            this.content = content;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }

}
