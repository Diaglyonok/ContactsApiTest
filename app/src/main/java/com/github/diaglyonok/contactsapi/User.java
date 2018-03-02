package com.github.diaglyonok.contactsapi;

/**
 * Created by diaglyonok on 02.03.18.
 */

public class User {
    private String sex;
    private String urlImage;
    private String name;
    private String surName;
    private String title;

    User(String sex, String urlImage, String title, String name, String surName){
        this.sex = sex;
        this.urlImage = urlImage;
        this.title = title;
        this.name = name;
        this.surName = surName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
