package com.wukong.dto;

/**
 * Created By WuKong on 2022/7/13 19:07
 **/
public class SearchBlog {

    private String title;

    private Long typeId;

    public SearchBlog() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
