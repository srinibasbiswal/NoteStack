package com.srinibasbiswal.notestack;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;

import java.io.File;

public class Upload extends AppCompatActivity {

    private Spinner noteBranch , noteSemester;
    private EditText noteSubject , noteTeacher , noteSection;
    private FirebaseStorage storage;
    private Button selectNote , upload;
    private TextView selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        storage = FirebaseStorage.getInstance();
        setupUIViews();

        selectNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,1212);
            }
        });
        //validate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1212:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = Upload.this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }
                    selectedNote.setText(displayName);
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);

    }



    private void setupUIViews(){
        noteBranch = (Spinner)findViewById(R.id.noteBranch);
        noteSemester = (Spinner)findViewById(R.id.noteSemester);
        noteSubject = (EditText)findViewById(R.id.noteSubject);
        noteTeacher = (EditText)findViewById(R.id.noteTeacher);
        noteSection = (EditText)findViewById(R.id.noteSection);
        selectNote = (Button)findViewById(R.id.selectNote);
        selectedNote = (TextView)findViewById(R.id.selectedNote);

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
