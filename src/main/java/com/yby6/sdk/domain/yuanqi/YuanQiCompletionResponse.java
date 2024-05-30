/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

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
