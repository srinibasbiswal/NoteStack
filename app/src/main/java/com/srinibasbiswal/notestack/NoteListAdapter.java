package com.srinibasbiswal.notestack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    public List<Notes> notesList;

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

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView noteSubject, noteBranch, noteSemester;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            noteSubject = (TextView)mView.findViewById(R.id.noteSubject);
            noteBranch = (TextView)mView.findViewById(R.id.noteBranch);
            noteSemester = (TextView)mView.findViewById(R.id.noteSemester);
        }
    }
}
