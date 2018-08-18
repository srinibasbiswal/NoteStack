package com.srinibasbiswal.notestack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Homepage extends AppCompatActivity {

    private RecyclerView noteList;
    private FirebaseFirestore db;

    private NoteListAdapter noteListAdapter;
    private List<Notes> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesList = new ArrayList<>();
        noteListAdapter = new NoteListAdapter(notesList);
        noteList = (RecyclerView)findViewById(R.id.noteList);
        db = FirebaseFirestore.getInstance();
        noteList.setLayoutManager(new LinearLayoutManager(Homepage.this));
        noteList.setAdapter(noteListAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Homepage.this,"Clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Homepage.this, Upload.class);
                startActivity(intent);
            }
        });

        db.collection("notes").document("(CSIT) Computer Science and Information Technology").collection("3rd").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

}
