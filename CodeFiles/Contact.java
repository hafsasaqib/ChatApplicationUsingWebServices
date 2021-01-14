package com.example.i170212_i170321;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contact implements Serializable {
    private String fname;
    private String lname;
    private String username;
    private String dob;
    private String gender;
    private String phone;
    private String bio;
    private String email;
    private String password;
    private boolean online;
    public List<Chat> chats;
    public String pic;

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> contacts;


    public Contact(){
        this.fname = "";
        this.lname = "";
        this.username = "";
        this.dob = "";
        this.gender = "";
        this.phone = "";
        this.bio = "";
        this.pic = "";

        this.online = false;
        this.chats = new ArrayList<Chat>();
        this.contacts = new ArrayList<Contact>();


    }
    public Contact(String fname, String lname, String dob, String gender, String phone, String bio) {
        this.fname = fname;
        this.lname = lname;
        this.username = fname+" "+lname;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.bio = bio;
        this.online = false;
        this.chats = new ArrayList<Chat>();
        this.contacts = new ArrayList<Contact>();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
