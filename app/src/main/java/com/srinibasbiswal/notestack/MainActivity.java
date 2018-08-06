package com.srinibasbiswal.notestack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText usrEmail, usrPswd;
    private Button loginBtn;
    private TextView regLink;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUIViews();
        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    final String email = usrEmail.getText().toString().trim();
                    final String password = usrPswd.getText().toString().trim();

                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.d("SignIn Process","Sucess");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                startActivity(new Intent(MainActivity.this,Homepage.class));
                            }
                            else{
                                Log.w("SignIn Process",task.getException());
                                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"Bahut Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , RegistrationActivity.class));
            }
        });
    }

    private void setupUIViews(){
        usrEmail = (EditText)findViewById(R.id.usrEmail);
        usrPswd = (EditText)findViewById(R.id.usrPswd);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        regLink = (TextView)findViewById(R.id.regLink);
    }

    private Boolean validate(){
        Boolean result = false;
        String email = usrEmail.getText().toString();
        String password = usrPswd.getText().toString();

        if (password.isEmpty() && email.isEmpty())
            Toast.makeText(this,"Please Enter All The Deatils",Toast.LENGTH_SHORT).show();
        else
            result  = true;

        return result;
    }
}
