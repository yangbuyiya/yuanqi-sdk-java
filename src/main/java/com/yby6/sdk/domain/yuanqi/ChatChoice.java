package com.yby6.sdk.domain.yuanqi;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 对话信息
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatChoice implements Serializable {


    @JsonProperty("finish_reason")
    private String finishReason;
    @JsonProperty("message")
    private MessageDTO message;

    @NoArgsConstructor
    @Data
    public static class MessageDTO {

        /**
         * 角色
         */
        @JsonProperty("role")
        private String role;

        /**
         * 返回的数据内容
         */
        @JsonProperty("content")
        private String content;


        @JsonProperty("steps")
        private List<StepsDTO> steps;

        @NoArgsConstructor
        @Data
        public static class StepsDTO {
            @JsonProperty("role")
            private String role;
            @JsonProperty("content")
            private String content;
            @JsonProperty("usage")
            private UsageDTO usage;
            @JsonProperty("time_cost")
            private Integer timeCost;

            @NoArgsConstructor
            @Data
            public static class UsageDTO {
                @JsonProperty("prompt_tokens")
                private Integer promptTokens;
                @JsonProperty("completion_tokens")
                private Integer completionTokens;
                @JsonProperty("total_tokens")
                private Integer totalTokens;
            }
        }
    }
}
