package com.douguo.ndc.model;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/12
 */
public class Event {
    private int pv;
    private int uv;
    private String eventCode;
    private String eventName;
    private String paramKey;
    private String paramValue;

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override public String toString() {
        return "Event{" + "pv=" + pv + ", uv=" + uv + ", eventCode='" + eventCode + '\'' + ", eventName='" + eventName
            + '\'' + ", paramKey='" + paramKey + '\'' + ", paramValue='" + paramValue + '\'' + '}';
    }
}
