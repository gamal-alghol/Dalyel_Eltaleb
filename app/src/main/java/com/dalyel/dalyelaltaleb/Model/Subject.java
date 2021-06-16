package com.dalyel.dalyelaltaleb.Model;

import com.google.firebase.firestore.Exclude;

public class Subject {
    private String filesUrl;

    @Exclude
  private String id;

    public Subject() {
    }
    public Subject(String filesUrl) {
        this.setFilesUrl(filesUrl);
    }


    public String getFilesUrl() {
        return filesUrl;
    }

    public void setFilesUrl(String filesUrl) {
        this.filesUrl = filesUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
