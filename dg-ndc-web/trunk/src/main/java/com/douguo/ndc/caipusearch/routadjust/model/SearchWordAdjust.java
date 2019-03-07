package com.douguo.ndc.caipusearch.routadjust.model;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/14
 */
public class SearchWordAdjust {
    private int id;
    private String key;
    private String type;
    private String exclude_key;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExclude_key() {
        return exclude_key;
    }

    public void setExclude_key(String exclude_key) {
        this.exclude_key = exclude_key;
    }

    @Override public String toString() {
        return "SearchWordAdjust{" + "id=" + id + ", key='" + key + '\'' + ", type='" + type + '\'' + ", exclude_key='"
            + exclude_key + '\'' + '}';
    }
}
