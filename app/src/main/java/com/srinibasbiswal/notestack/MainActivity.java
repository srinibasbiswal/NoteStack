package com.srinibasbiswal.notestack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText usrEmail, usrPswd;
    private Button loginBtn;
    private TextView regLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUIViews();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    // login code
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
