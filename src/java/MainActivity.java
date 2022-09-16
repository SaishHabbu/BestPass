package com.example.bestpass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.bestpass.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    //
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    ConstraintLayout mMainLayout;
    //
    ActivityMainBinding binding;

    private PasswordViewModel passwordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //
        mMainLayout=findViewById(R.id.main_layout);

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()){
            case  BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Device Does not have Fingerprint", Toast.LENGTH_SHORT).show();
                break;
            case  BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, " Fingerprint Not Working", Toast.LENGTH_SHORT).show();

            case  BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "No Fingerprint Assigned", Toast.LENGTH_SHORT).show();
                mMainLayout.setVisibility(View.VISIBLE);
        }
        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

            }

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                mMainLayout.setVisibility(View.VISIBLE);
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Best Pass")
                .setDescription("Use FingerPrint To Login").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);

        //

        passwordViewModel= new ViewModelProvider(this,(ViewModelProvider.Factory)ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()) )
                .get(PasswordViewModel.class);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DataInsertActivity.class);
                intent.putExtra("type","addMode");
                startActivityForResult(intent,1);
            }
        });

        binding.Rv.setLayoutManager(new LinearLayoutManager(this));
        binding.Rv.setHasFixedSize(true);

        RVAdapter adapter = new RVAdapter(MainActivity.this);
        binding.Rv.setAdapter(adapter);

        passwordViewModel.getAllPassword().observe(this, new Observer<List<Password>>() {
            @Override
            public void onChanged(List<Password> passwords) {
                adapter.submitList(passwords);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback( 0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == ItemTouchHelper.RIGHT){

                    Toast.makeText(MainActivity.this, "Item will be Deleted", Toast.LENGTH_SHORT).show();
                    passwordViewModel.delete(adapter.getPassword(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(MainActivity.this, "Update", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,DataInsertActivity.class);
                    intent.putExtra("type","update");
                    intent.putExtra("signin",adapter.getPassword(viewHolder.getAdapterPosition()).getSigninName());
                    intent.putExtra("username",adapter.getPassword(viewHolder.getAdapterPosition()).getUsername());
                    intent.putExtra("password",adapter.getPassword(viewHolder.getAdapterPosition()).getPassword());
                    intent.putExtra("id",adapter.getPassword(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent,2);



                }

            }
        }).attachToRecyclerView(binding.Rv);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            String signin= data.getStringExtra("signin");
            String userid= data.getStringExtra("userid");
            String password= data.getStringExtra("password");
            Password password1 = new Password(signin,userid,password);
            passwordViewModel.insert(password1);
            Toast.makeText(this, "Sign-in Details Added Successfully", Toast.LENGTH_SHORT).show();



        }
        else  if (requestCode==2){
            String signin= data.getStringExtra("signin");
            String userid= data.getStringExtra("userid");
            String password= data.getStringExtra("password");
            Password password1 = new Password(signin,userid,password);
            password1.setId(data.getIntExtra("id",0));
            passwordViewModel.update(password1);
            Toast.makeText(this, "Sign-in Details Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}