package com.wolf.extra.video.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 短地址请求body
 * 
 */
public class ShortEncodeRequest {

    @JsonProperty("urlStr")
    private String urlStr;

    private String domain;

    /**
     * 过期时间
     * 1 7天
     * 2 1个月
     * 3 3个月
     * 4 6个月
     * 5 1年
     * 6 永久
     */
    private String expireType;

    private String key = "0@null";

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getExpireType() {
        return expireType;
    }

    public void setExpireType(String expireType) {
        this.expireType = expireType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
