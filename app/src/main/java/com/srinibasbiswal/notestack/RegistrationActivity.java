package com.srinibasbiswal.notestack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usrName, usrEmail, usrPswd;
    private Button regBtn;
    private TextView loginLink;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    //update on db
                    final String user_name = usrName.getText().toString();
                    final String user_email = usrEmail.getText().toString().trim();
                    String user_password = usrPswd.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, CompleteDetailsActivity.class);
                                intent.putExtra("user_name",user_name);
                                intent.putExtra("user_email",user_email);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this , MainActivity.class));
            }
        });
    }

    private void setupUIViews(){
        usrName = (EditText)findViewById(R.id.usrName);
        usrEmail = (EditText)findViewById(R.id.usrEmail);
        usrPswd = (EditText)findViewById(R.id.usrPswd);
        regBtn = (Button)findViewById(R.id.regBtn);
        loginLink = (TextView)findViewById(R.id.loginLink);
    }

    private Boolean validate(){
        Boolean result = false;
        String name = usrName.getText().toString();
        String email = usrEmail.getText().toString();
        String password = usrPswd.getText().toString();

        if (name.isEmpty() && password.isEmpty() && email.isEmpty())
            Toast.makeText(this,"Please Enter All The Deatils",Toast.LENGTH_SHORT).show();
        else
            result  = true;

        return result;
    }
}
