package com.yby6.sdk.domain.yuanqi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class a {


    @JsonProperty("id")
    private String id;
    @JsonProperty("created")
    private Integer created;
    @JsonProperty("choices")
    private List<Choices> choices;
    @JsonProperty("assistant_id")
    private String assistantId;
    @JsonProperty("usage")
    private Usage usage;

    @NoArgsConstructor
    @Data
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private Integer promptTokens;
        @JsonProperty("completion_tokens")
        private Integer completionTokens;
        @JsonProperty("total_tokens")
        private Integer totalTokens;
    }

    @NoArgsConstructor
    @Data
    public static class Choices {
        @JsonProperty("finish_reason")
        private String finishReason;
        @JsonProperty("message")
        private Message message;

        @NoArgsConstructor
        @Data
        public static class Message {
            @JsonProperty("role")
            private String role;
            @JsonProperty("content")
            private String content;
            @JsonProperty("steps")
            private List<Steps> steps;

            @NoArgsConstructor
            @Data
            public static class Steps {
                @JsonProperty("role")
                private String role;
                @JsonProperty("content")
                private String content;
                @JsonProperty("usage")
                private Usage usage;
                @JsonProperty("time_cost")
                private Integer timeCost;

                @NoArgsConstructor
                @Data
                public static class Usage {
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
}
