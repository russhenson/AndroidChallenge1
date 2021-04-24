package com.mobdeve.hensonruss.androidchallenge1;

public class Email {
    private String receiver, subject, body;

    public Email(String receiver, String subject, String body){
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
