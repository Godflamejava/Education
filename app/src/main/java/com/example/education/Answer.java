package com.example.education;

public class Answer {
    public String submitter,link;
    public Answer(){}
    public Answer(String submitter,String link){
        this.submitter=submitter;
        this.link=link;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
