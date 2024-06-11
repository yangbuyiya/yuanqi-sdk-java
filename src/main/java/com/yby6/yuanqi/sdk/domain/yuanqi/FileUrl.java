/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.yby6.yuanqi.sdk.domain.yuanqi;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class FileUrl implements Serializable {
    
    /**
     * 文件的类型，例如image/video/audio/pdf/doc/txt等
     */
    private String type;
    
    /**
     * 文件的url
     */
    private String image_url;
    
    public FileUrl(Builder builder) {
        this.type = builder.type;
        this.image_url = builder.url;
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
         * 文件的url
         */
        private String url;
        
        public Builder() {
        }
        
        public Builder type(String type) {
            this.type = type;
            return this;
        }
        
        public Builder url(String url) {
            this.url = url;
            return this;
        }
        
        public FileUrl build() {
            return new FileUrl(this);
        }
    }
    
    
}
