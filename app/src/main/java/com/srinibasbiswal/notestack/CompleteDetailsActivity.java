package com.srinibasbiswal.notestack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CompleteDetailsActivity extends AppCompatActivity {

    private Spinner usrBranch, usrSemester;
    private EditText usrSection, usrPassYr;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_details);
        setupUIViews();
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    String temp =usrBranch.getSelectedItem().toString()+usrSection.getText().toString()+usrSemester.getSelectedItem().toString()+usrPassYr.getText().toString();
                    Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setupUIViews(){
        usrBranch = (Spinner)findViewById(R.id.usrBranch);
        usrSection = (EditText)findViewById(R.id.usrSection);
        usrSemester = (Spinner)findViewById(R.id.usrSemester);
        usrPassYr = (EditText)findViewById(R.id.usrPassYr);
        startBtn = (Button)findViewById(R.id.startBtn);

        ArrayAdapter<CharSequence> branchAdapter = ArrayAdapter.createFromResource(this,R.array.branchNames, android.R.layout.simple_spinner_item);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usrBranch.setAdapter(branchAdapter);

        ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(this,R.array.semesterNames, android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usrSemester.setAdapter(semesterAdapter);

    }

    private Boolean validate(){
        Boolean result = false;
        String branch = usrBranch.getSelectedItem().toString();
        String section = usrSection.getText().toString();
        String semester = usrSemester.getSelectedItem().toString();
        String passsYear = usrPassYr.getText().toString();

        if (branch.equals("Select Branch") && section.isEmpty() && semester.equals("Select Semester") && passsYear.isEmpty())
            Toast.makeText(this,"Please Enter All The Details",Toast.LENGTH_SHORT).show();
        else
            result = true;

        return result;
    }

}
