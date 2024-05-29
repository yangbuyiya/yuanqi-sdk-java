package com.yby6.sdk.domain.yuanqi;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消耗额度
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usage {


    /**
     * 提示令牌
     */
    @JsonProperty("prompt_tokens")
    private Integer promptTokens;
    /**
     * 完成令牌
     */
    @JsonProperty("completion_tokens")
    private Integer completionTokens;
    /**
     * 代币总数
     */
    @JsonProperty("total_tokens")
    private Integer totalTokens;
}
