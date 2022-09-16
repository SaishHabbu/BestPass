package com.example.bestpass;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_passwords")
public class Password {
    private  String signinName;
    private  String username;
    private  String password;

    @PrimaryKey(autoGenerate= true)
    private  int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Password(String signinName, String username, String password ) {
        this.signinName = signinName;
        this.username = username;
        this.password = password;

    }

    public String getSigninName() {
        return signinName;
    }

    public void setSigninName(String signinName) {
        this.signinName = signinName;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
