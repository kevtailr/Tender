package com.techelevator.model;

import java.sql.Date;
import java.sql.Time;

public class Invite {

    private int inviteId;
    private int senderUserId;
    private Date closingDate;
    private Time closingTime;
    private String uniqueLink;

    public Invite(int inviteId, int senderUserId, Date closingDate, Time closingTime, String uniqueLink) {
        this.inviteId = inviteId;
        this.senderUserId = senderUserId;
        this.closingDate = closingDate;
        this.closingTime = closingTime;
        this.uniqueLink = uniqueLink;
    }

    public Invite() {}

    public int getInviteId() {
        return inviteId;
    }

    public void setInviteId(int inviteId) {
        this.inviteId = inviteId;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Time getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }

    public String getUniqueLink() {
        return uniqueLink;
    }

    public void setUniqueLink(String uniqueLink) {
        this.uniqueLink = uniqueLink;
    }
}
