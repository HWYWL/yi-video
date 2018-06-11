package com.yi.model;

import java.io.Serializable;

/**
 * @author 
 */
public class SearchRecords implements Serializable {
    private String id;

    /**
     * 搜索的内容
     */
    private String content;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}