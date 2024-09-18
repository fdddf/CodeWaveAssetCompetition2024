package com.fdddf.dingding;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netease.lowcode.core.annotation.NaslStructure;

@NaslStructure
public class UploadResponse {
    public Integer errcode;

    public String errmsg;

    public String media_id;

    public Long created_at;

    public String type;

    @Override
    public String toString() {
        return "UploadResponse{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", media_id='" + media_id + '\'' +
                ", created_at=" + created_at +
                ", type='" + type + '\'' +
                '}';
    }
}