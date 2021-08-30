package com.example.education;

public class Lecture {
    public String link,time;
 public    Lecture(){

 }
 public Lecture(String link,String time){
     this.link=link;
     this.time=time;
 }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
