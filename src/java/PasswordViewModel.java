package com.example.bestpass;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PasswordViewModel extends AndroidViewModel {
    private PasswordRepo passwordRepo;
    private LiveData<List<Password>>passwordlist;
    public PasswordViewModel(@NonNull Application application) {
        super(application);
        passwordRepo = new PasswordRepo(application);
        passwordlist = passwordRepo.getAllData();

    }
    public  void  insert(Password password){
        passwordRepo.insertData(password);
    }
    public  void  delete(Password password){
        passwordRepo.deleteData(password);
    }
    public  void  update(Password password){
        passwordRepo.updateData(password);
    }

    public  LiveData<List<Password>> getAllPassword(){
        return  passwordlist;
    }
}
