package com.srinibasbiswal.notestack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Upload extends AppCompatActivity {

    private Spinner noteBranch , noteSemester;
    private EditText noteSubject , noteTeacher , noteSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        setupUIViews();
        validate();
    }

    private void setupUIViews(){
        noteBranch = (Spinner)findViewById(R.id.noteBranch);
        noteSemester = (Spinner)findViewById(R.id.noteSemester);
        noteSubject = (EditText)findViewById(R.id.noteSubject);
        noteTeacher = (EditText)findViewById(R.id.noteTeacher);
        noteSection = (EditText)findViewById(R.id.noteSection);

        ArrayAdapter<CharSequence> branchAdapter = ArrayAdapter.createFromResource(this,R.array.branchNames, android.R.layout.simple_spinner_item);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteBranch.setAdapter(branchAdapter);

        ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(this,R.array.semesterNames, android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteSemester.setAdapter(semesterAdapter);
    }

    private Boolean validate(){
        Boolean result = false;
        String Branch = noteBranch.getSelectedItem().toString();
        String Semester = noteSemester.getSelectedItem().toString();
        String Subject = noteSubject.getText().toString();
        String Section = noteSection.getText().toString();

        if (Branch.equals("Select Branch") && Section.isEmpty() && Semester.equals("Select Semester") && Subject.isEmpty())
            Toast.makeText(this,"Please Enter All The Details",Toast.LENGTH_SHORT).show();
        else
            result = true;

        return result;
    }
}
