package com.example.education;

public class Classroom {
    public String id,teacher,subject,section,User;

    public Classroom(String id, String teacher, String subject,String section,String user ){
      this.id=id;
      this.teacher=teacher;
      this.section=section;
      this.subject=subject;
      User=user;
    }

    public Classroom(){

    }


    public void setId(String id){
        this.id=id;
    }

    public String getId(){
        return id;
    }
    public void setTeacher(String teacher){
        this.teacher=teacher;
    }

    public String getTeacher(){
        return teacher;
    }
    public void setSubject(String subject){
        this.subject=subject;
    }

    public String getSubject(){
        return subject;
    }
    public void setSection(String section){
        this.section=section;
    }

    public String getSection(){
        return section;
    }


}
