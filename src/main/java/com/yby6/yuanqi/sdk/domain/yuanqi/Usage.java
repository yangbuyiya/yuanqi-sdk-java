/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.yby6.yuanqi.sdk.domain.yuanqi;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
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
