package com.dalyel.dalyelaltaleb.Model;

public class Comment {
   private String commentKey = "";
    private String comment = "";
    private long commentTimeInMillis;
    private String commentedByUserKey = "";

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    private String fromEmail = "";

    public Comment(String comment, long commentTimeInMillis, String commentedByUserKey) {

        this.comment = comment;
        this.commentTimeInMillis = commentTimeInMillis;
        this.commentedByUserKey = commentedByUserKey;
    }


    public Comment(String commentKey, String comment, long commentTimeInMillis, String commentedByUserKey) {
        this.commentKey = commentKey;
        this.comment = comment;
        this.commentTimeInMillis = commentTimeInMillis;
        this.commentedByUserKey = commentedByUserKey;
    }

    public Comment() {
    }

    public long getCommentTimeInMillis() {
        return commentTimeInMillis;
    }

    public void setCommentTimeInMillis(long commentTimeInMillis) {
        this.commentTimeInMillis = commentTimeInMillis;
    }

    public String getCommentKey() {
        if (commentKey == null)
            return "";
        return commentKey;
    }

    public void setCommentKey(String commentKey) {
        this.commentKey = commentKey;
    }

    public String getComment() {
        if (comment == null)
            return "";
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentedByUserKey() {
        if (commentedByUserKey == null)
            return "";
        return commentedByUserKey;
    }

    public void setCommentedByUserKey(String commentedByUserKey) {
        this.commentedByUserKey = commentedByUserKey;
    }


}
