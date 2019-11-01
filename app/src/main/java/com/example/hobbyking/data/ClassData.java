package com.example.hobbyking.data;

import java.io.Serializable;

public class ClassData implements Serializable {
    String name;
    String detail;
    String tutor_info;
    int price;
    String image_url;
    double rating;
    int people_num;
    String duedate;
    int limit_people_num;
    String date_time;
    String date_week;
    int class_time;
    public ClassData() {
    }

    public ClassData(String name, String detail, String tutor_info, int price, String image_url, double rating, int people_num, String duedate, int limit_people_num, String date_time, String date_week, int class_time) {
        this.name = name;
        this.detail = detail;
        this.tutor_info = tutor_info;
        this.price = price;
        this.image_url = image_url;
        this.rating = rating;
        this.people_num = people_num;
        this.duedate = duedate;
        this.limit_people_num = limit_people_num;
        this.date_time = date_time;
        this.date_week = date_week;
        this.class_time = class_time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setTutor_info(String tutor_info) {
        this.tutor_info = tutor_info;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setPeople_num(int people_num) {
        this.people_num = people_num;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public void setLimit_people_num(int limit_people_num) {
        this.limit_people_num = limit_people_num;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setDate_week(String date_week) {
        this.date_week = date_week;
    }

    public void setClass_time(int class_time) {
        this.class_time = class_time;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public String getTutor_info() {
        return tutor_info;
    }

    public int getPrice() {
        return price;
    }

    public String getImage_url() {
        return image_url;
    }

    public double getRating() {
        return rating;
    }

    public int getPeople_num() {
        return people_num;
    }

    public String getDuedate() {
        return duedate;
    }

    public int getLimit_people_num() {
        return limit_people_num;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getDate_week() {
        return date_week;
    }

    public int getClass_time() {
        return class_time;
    }
}
