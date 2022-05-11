package com.example.mathsolver.Models;

import java.util.Date;

public class Message {
 public String userName;
    public String txtMessage;
    private long msgTime;

    public Message() {}
    public Message(String userName, String txtMessage){
        this.userName = userName;
        this.txtMessage = txtMessage;
        this.msgTime = new Date().getTime();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTxtMessage() {
        return txtMessage;
    }

    public void setTxtMessage(String txtMessage) {
        this.txtMessage = txtMessage;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }
}
