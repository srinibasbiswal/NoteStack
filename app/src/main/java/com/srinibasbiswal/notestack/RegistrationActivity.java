package com.srinibasbiswal.notestack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usrName, usrEmail, usrPswd;
    private Button regBtn;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    //update on db
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
