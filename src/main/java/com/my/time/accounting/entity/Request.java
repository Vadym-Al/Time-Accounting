package com.my.time.accounting.entity;

import java.util.Objects;

public class Request {
    private long requestId;
    private String about;
    private long activityId;
    private long administratorId;
    private long userId;

    public static Request createRequest(String about, long activityId, long administratorId, long userId){
        Request request = new Request();
        request.setAbout(about);
        request.setActivityId(activityId);
        request.setAdministratorId(administratorId);
        request.setUserId(userId);
        return request;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(long administratorId) {
        this.administratorId = administratorId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", about='" + about + '\'' +
                ", activityId=" + activityId +
                ", administratorId=" + administratorId +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return activityId == request.activityId && administratorId == request.administratorId && Objects.equals(about, request.about);
    }

    @Override
    public int hashCode() {
        return Objects.hash(about, activityId, administratorId);
    }
}
