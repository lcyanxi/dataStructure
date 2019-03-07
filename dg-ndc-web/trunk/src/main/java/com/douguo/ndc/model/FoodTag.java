package com.douguo.ndc.model;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/28
 */
public class FoodTag {
    private int id;
    private int foodId;
    private String foodName;
    private String tag;
    private float confidenceInterval;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public java.lang.String getFoodName() {
        return foodName;
    }

    public void setFoodName(java.lang.String foodName) {
        this.foodName = foodName;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override public String toString() {
        return "FoodTag{" + "id=" + id + ", foodId=" + foodId + ", foodName='" + foodName + '\'' + ", tag='" + tag
            + '\'' + ", confidenceInterval=" + confidenceInterval + ", type=" + type + '}';
    }
}
