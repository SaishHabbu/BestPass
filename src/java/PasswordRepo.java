package com.example.bestpass;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PasswordRepo {
    private PasswordDao passwordDao;
    private LiveData<List<Password>> passwordlist;

    public PasswordRepo(Application application){
        PasswordDatabase passwordDatabase =PasswordDatabase.getInstance(application);
        passwordDao=passwordDatabase.passwordDao();
        passwordlist=passwordDao.getAllData();
    }
    public  void insertData(Password password){new InsertTask(passwordDao).execute(password);}
    public  void updateData(Password password){new UpdateTask(passwordDao).execute(password);}
    public  void deleteData(Password password){new DeleteTask(passwordDao).execute(password);}
    public LiveData<List<Password>> getAllData(){
        return  passwordlist;
    }

    private  static class  InsertTask extends AsyncTask<Password,Void,Void>{
        private  PasswordDao passwordDao;

        public InsertTask(PasswordDao passwordDao) {
            this.passwordDao = passwordDao;
        }

        @Override
        protected Void doInBackground(Password... passwords) {
            passwordDao.insert(passwords[0]);
            return null;
        }
    }
    private  static class  DeleteTask extends AsyncTask<Password,Void,Void>{
        private  PasswordDao passwordDao;

        public DeleteTask(PasswordDao passwordDao) {
            this.passwordDao = passwordDao;
        }

        @Override
        protected Void doInBackground(Password... passwords) {
            passwordDao.delete(passwords[0]);
            return null;
        }
    }
    private  static class  UpdateTask extends AsyncTask<Password,Void,Void>{
        private  PasswordDao passwordDao;

        public UpdateTask(PasswordDao passwordDao) {
            this.passwordDao = passwordDao;
        }

        @Override
        protected Void doInBackground(Password... passwords) {
            passwordDao.update(passwords[0]);
            return null;
        }
    }

}
