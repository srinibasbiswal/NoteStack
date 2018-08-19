package com.srinibasbiswal.notestack;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Upload extends AppCompatActivity {

    private Spinner noteBranch , noteSemester;
    private EditText noteSubject , noteTeacher , noteSection;
    private FirebaseStorage storage= FirebaseStorage.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private StorageReference filepath;
    private Button selectNote , upload;
    private TextView selectedNote;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
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

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    String name = user.getDisplayName();
                                    Map<String,Object> note = new HashMap<>();
                                    note.put("noteSubject",noteSubject.getText().toString());
                                    note.put("noteBranch",noteBranch.getSelectedItem().toString());
                                    note.put("noteSemester",noteSemester.getSelectedItem().toString());
                                    note.put("noteTeacher",noteTeacher.getText().toString());
                                    note.put("downloadURL",downloadUrl.toString());
                                    note.put("studentName",user.getDisplayName().toString());

                                    db.collection("notes").document(noteBranch.getSelectedItem().toString()).collection(noteSemester.getSelectedItem().toString()).add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(Upload.this, " Added Success" , Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Upload.this, " Upload Failed" , Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            Toast.makeText(Upload.this, " Upload Success" , Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Upload.this,"Upload Failure", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1212:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    uri = data.getData();
                    filepath = storage.getReference().child(noteBranch.getSelectedItem().toString().trim()).child(noteSemester.getSelectedItem().toString().trim()).child(uri.getLastPathSegment());                    String uriString = uri.toString();
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
        upload = (Button)findViewById(R.id.upload);

        ArrayAdapter<CharSequence> branchAdapter = ArrayAdapter.createFromResource(this,R.array.branchNames, android.R.layout.simple_spinner_item);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteBranch.setAdapter(branchAdapter);

        ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(this,R.array.semesterNames, android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteSemester.setAdapter(semesterAdapter);
    }

    private Boolean validate(){
        Boolean result = false;
        String Branch = noteBranch.getSelectedItem().toString().trim();
        String Semester = noteSemester.getSelectedItem().toString().trim();
        String Subject = noteSubject.getText().toString();
        String Section = noteSection.getText().toString();
        String SelectedNote = selectedNote.getText().toString();

        if (Branch.equals("SelectBranch") && Section.isEmpty() && Semester.equals("SelectSemester") && Subject.isEmpty() && SelectedNote.equals(""))
            Toast.makeText(this,"Please Enter All The Details",Toast.LENGTH_SHORT).show();
        else
            result = true;

        return result;
    }
}
