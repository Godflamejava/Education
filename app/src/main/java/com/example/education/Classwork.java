package com.example.education;

import com.google.android.gms.common.util.Strings;

public class Classwork {
    public String title,topic,date,marks,cid,description,uid;
    public Classwork(){

    }
    public Classwork(String title, String topic, String date, String marks, String cid,String description,String uid){
        this.title=title;
        this.topic=topic;
        this.date=date;
        this.marks=marks;
        this.cid=cid;
        this.description=description;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTopic() {
        return topic;
    }

    public String getDate() {
        return date;
    }

    public String getMarks() {
        return marks;
    }

    public String getCid() {
        return cid;
    }
}