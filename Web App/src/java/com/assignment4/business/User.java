package com.assignment4.business;

import java.io.Serializable;

public class User implements Serializable {

    private String Name;
    private String Email;
    private String Type;
    private int NumCoins;
    private int NumPostedStudies;
    private int NumParticipation;

    public User() {
        Name = "";
        Email = "";
        Type = "";
        NumCoins=0;
        NumPostedStudies=0;
        NumParticipation=0;
    }

    public User(String Name, String Email, String Type,int NumCoins,
            int NumPostedStudies,int NumParticipation) {
        this.Name = Name;
        this.Email = Email;
        this.Type = Type;
        this.NumCoins=NumCoins;
        this.NumPostedStudies=NumPostedStudies;
        this.NumParticipation=NumParticipation;
    }
    
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    
    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getNumCoins() {
        return NumCoins;
    }

    public void setNumCoins(int NumCoins) {
        this.NumCoins = NumCoins;
    }

    public int getNumPostedStudies() {
        return NumPostedStudies;
    }

    public void setNumPostedStudies(int NumPostedStudies) {
        this.NumPostedStudies = NumPostedStudies;
    }

    public int getNumParticipation() {
        return NumParticipation;
    }

    public void setNumParticipation(int NumParticipation) {
        this.NumParticipation = NumParticipation;
    }  
    
}