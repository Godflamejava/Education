package com.example.education;

public class User {
    public String username,email,Classroom,choice;
    public User(){

    }
    public User(String username, String email,String classroom,String choice){
        this.username=username;
        this.email=email;
        Classroom= classroom;
        this.choice=choice;
    }



    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
