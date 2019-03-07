package com.douguo.ndc.model;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2019/1/15
 */
public class TencentStat {
    private int id;
    private String name;
    private String tagId;
    private String tagCode;
    private String createTime;
    private String parentTagId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getParentTagId() {
        return parentTagId;
    }

    public void setParentTagId(String parentTagId) {
        this.parentTagId = parentTagId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override public String toString() {
        return "TencentStat{" + "id=" + id + ", name='" + name + '\'' + ", tagId='" + tagId + '\'' + ", tagCode='"
            + tagCode + '\'' + '}';
    }
}
