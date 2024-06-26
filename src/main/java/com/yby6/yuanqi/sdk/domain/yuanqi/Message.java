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
     * 角色, 'user' 或者'assistant', 在message中必须是user与assistant交替(一问一答)
     */
    private String role;

    /**
     * 会话内容, 长度最多为40, 按对话时间从旧到新在数组中排列
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
