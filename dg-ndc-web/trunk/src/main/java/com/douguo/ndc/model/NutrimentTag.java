package com.douguo.ndc.model;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/28
 */
public class NutrimentTag {
    private  int id;
    private String nutrimentName;
    private String nutrimentId;
    private String tag;
    private float confidenceInterval;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNutrimentName() {
        return nutrimentName;
    }

    public void setNutrimentName(String nutrimentName) {
        this.nutrimentName = nutrimentName;
    }

    public String getNutrimentId() {
        return nutrimentId;
    }

    public void setNutrimentId(String nutrimentId) {
        this.nutrimentId = nutrimentId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getConfidenceInterval() {
        return confidenceInterval;
    }

    public void setConfidenceInterval(float confidenceInterval) {
        this.confidenceInterval = confidenceInterval;
    }

    @Override public String toString() {
        return "NutrimentTag{" + "id=" + id + ", nutrimentName='" + nutrimentName + '\'' + ", nutrimentId="
            + nutrimentId + ", tag='" + tag + '\'' + ", confidenceInterval=" + confidenceInterval + '}';
    }
}
