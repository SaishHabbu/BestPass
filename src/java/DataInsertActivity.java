package com.example.bestpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bestpass.databinding.ActivityDataInsertBinding;

public class DataInsertActivity extends AppCompatActivity {
ActivityDataInsertBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDataInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type=getIntent().getStringExtra("type");
        if(type.equals("update")){
            setTitle("Update");
            binding.signinTxt.setText(getIntent().getStringExtra("signin"));
            binding.usernameTxt.setText(getIntent().getStringExtra("username"));
            binding.passwordTxt.setText(getIntent().getStringExtra("password"));
            int id =getIntent().getIntExtra("id",0);
            binding.add.setText("Update ");
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("signin", binding.signinTxt.getText().toString());
                    intent.putExtra("userid", binding.usernameTxt.getText().toString());
                    intent.putExtra("password", binding.passwordTxt.getText().toString());
                    intent.putExtra("id",id);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            });

        }
        else {


            setTitle("Add Mode");
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("signin", binding.signinTxt.getText().toString());
                    intent.putExtra("userid", binding.usernameTxt.getText().toString());
                    intent.putExtra("password", binding.passwordTxt.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DataInsertActivity.this,MainActivity.class));
    }
}