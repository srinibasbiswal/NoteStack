package com.srinibasbiswal.notestack;

public class Notes {

    String noteSubject, noteSemester, noteBranch, downloadURL;

    public Notes(){

    }

    public  Notes(String noteSubject, String noteSemester, String noteBranch, String downloadURL){
        this.noteSubject = noteSubject;
        this.noteSemester = noteSemester;
        this.noteBranch = noteBranch;
        this.downloadURL = downloadURL;
    }

    public String getNoteSubject() {
        return noteSubject;
    }

    public void setNoteSubject(String noteSubject) {
        this.noteSubject = noteSubject;
    }

    public String getNoteSemester() {
        return noteSemester;
    }

    public void setNoteSemester(String noteSemester) {
        this.noteSemester = noteSemester;
    }

    public String getNoteBranch() {
        return noteBranch;
    }

    public void setNoteBranch(String noteBranch) {
        this.noteBranch = noteBranch;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }


}
