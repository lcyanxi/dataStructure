package com.douguo.ndc.model;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/28
 */
public class TagInfo {
    private int id;
    private String tag;
    private String type;
    private int sort;
    private int level;
    private String desc;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    @Override public String toString() {
        return "TagInfo{" + "id=" + id + ", tag='" + tag + '\'' + ", type='" + type + '\'' + ", sort=" + sort
            + ", level=" + level + ", desc='" + desc + '\'' + '}';
    }
}
