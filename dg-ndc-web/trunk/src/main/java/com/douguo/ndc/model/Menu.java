package com.douguo.ndc.model;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/10/8
 */
public class Menu {
    private int pId;
    private String name;
    private String url;
    private String icon;
    private int type;
    private int hasChild;
    private int visiable;

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHasChild() {
        return hasChild;
    }

    public void setHasChild(int hasChild) {
        this.hasChild = hasChild;
    }

    public int getVisiable() {
        return visiable;
    }

    public void setVisiable(int visiable) {
        this.visiable = visiable;
    }

    @Override public String toString() {
        return "Menu{" + "pId=" + pId + ", name='" + name + '\'' + ", url='" + url + '\'' + ", icon='" + icon + '\''
            + ", type=" + type + ", hasChild=" + hasChild + ", visiable=" + visiable + '}';
    }
}
