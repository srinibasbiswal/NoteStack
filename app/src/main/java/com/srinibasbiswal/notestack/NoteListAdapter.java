package com.srinibasbiswal.notestack;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    public List<Notes> notesList;
    public String url;

    public NoteListAdapter(List<Notes> notesList){
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_note,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.noteSubject.setText(notesList.get(i).getNoteSubject());
        viewHolder.noteBranch.setText(notesList.get(i).getNoteBranch());
        viewHolder.noteSemester.setText(notesList.get(i).getNoteSemester());
        viewHolder.urldemo.setText(notesList.get(i).getDownloadURL());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView noteSubject, noteBranch, noteSemester,urldemo;
        public Button download;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            noteSubject = (TextView)mView.findViewById(R.id.noteSubject);
            noteBranch = (TextView)mView.findViewById(R.id.noteBranch);
            noteSemester = (TextView)mView.findViewById(R.id.noteSemester);
            download = (Button)mView.findViewById(R.id.download);
            urldemo = (TextView)mView.findViewById(R.id.urldemo);
        }
    }
}
