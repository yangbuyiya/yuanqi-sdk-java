package com.yby6.sdk.domain.yuanqi;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 对话请求结果信息
 *
 * @author Yang Shuai
 * Create By 2024/05/29
 */
@Data
public class YuanQiCompletionResponse implements Serializable {

    /** ID */
    private String id;
    /** 对话 */
    private List<ChatChoice> choices;
    /** 创建 */
    private long created;
    /** 消耗 */
    private Usage usage;

    @JsonProperty("assistant_id")
    private String assistantId;

}
