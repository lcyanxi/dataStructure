package com.douguo.ndc.model;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/24
 */
public class CookTag implements Serializable{
    private int id;
    private int cookId;
    private String cookName;
    private int tagId;
    private String tag;
    private int status;
    private int level;
    private String createTime;
    private String updateTime;

    private String statusDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public int getCookId() {
        return cookId;
    }

    public void setCookId(int cookId) {
        this.cookId = cookId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCreateTime() {
        return createTime.substring(0, 10);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime.substring(0, 10);
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    @Override public String toString() {
        return "CookTag{" + "id=" + id + ", cookId=" + cookId + ",cookName=" + cookName + ", tagId=" + tagId + ", tag='" + tag + '\''
            + ", status=" + status + ", level=" + level + ", createTime='" + createTime + '\'' + ", updateTime='"
            + updateTime + '\'' + '}';
    }
}
