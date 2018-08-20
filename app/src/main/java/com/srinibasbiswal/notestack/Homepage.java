package com.srinibasbiswal.notestack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferRef;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Homepage extends AppCompatActivity {

    private RecyclerView noteList;
    private FirebaseFirestore db;
    private Button download;
    private TextView urldemo;
    private String branch,semester;

    private NoteListAdapter noteListAdapter;
    private List<Notes> notesList;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesList = new ArrayList<>();
        noteListAdapter = new NoteListAdapter(notesList);
        noteList = (RecyclerView)findViewById(R.id.noteList);
        download =(Button)findViewById(R.id.download);
        urldemo =(TextView)findViewById(R.id.urldemo);
        db = FirebaseFirestore.getInstance();
        noteList.setLayoutManager(new LinearLayoutManager(Homepage.this));
        noteList.setAdapter(noteListAdapter);

        String uid = user.getUid();

        db.collection("users").whereEqualTo("uid",uid.toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot document : task.getResult()) {
                        //Toast.makeText(Homepage.this, document.get("UserBranch").toString(), Toast.LENGTH_SHORT).show();
                        branch = document.get("UserBranch").toString();
                        semester = document.get("UserSemester").toString();
                        Toast.makeText(Homepage.this,branch,Toast.LENGTH_SHORT).show();
                        Toast.makeText(Homepage.this,semester,Toast.LENGTH_SHORT).show();
                        db.collection("notes").document(branch).collection(semester).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (e != null){
                                    Log.d("Test","Error: "+e.getMessage());
                                }
                                for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                                    if(doc.getType() == DocumentChange.Type.ADDED){
                                        Notes note = doc.getDocument().toObject(Notes.class);
                                        notesList.add(note);

                                        noteListAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(Homepage.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Homepage.this,"Clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Homepage.this, Upload.class);
                startActivity(intent);
            }
        });





    }

}
