package com.employee.system.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShortenRequestVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6925304990834587266L;
    private String url;

    @JsonCreator
    public ShortenRequestVo() {

    }

    @JsonCreator
    public ShortenRequestVo(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
