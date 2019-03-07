package com.douguo.ndc.model;

import java.io.Serializable;

/**
 * Created by lcyanxi on 2018/8/12.
 */
public class User implements Serializable {

    private int id;
    private String uid;
    private String pass;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override public String toString() {
        return "User{" + "id=" + id + ", uid='" + uid + '\'' + ", pass='" + pass + '\'' + ", username='" + username
            + '\'' + '}';
    }
}
