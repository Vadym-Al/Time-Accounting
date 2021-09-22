package com.my.time.accounting.entity;

import java.util.Objects;

public class Activity {
    private long activityId;
    private String name;
    private String about;
    private long administratorId;

    public static Activity createActivity(String name, String about, long administratorId){
        Activity activity = new Activity();
        activity.setName(name);
        activity.setAbout(about);
        activity.setAdministratorId(administratorId);
        return activity;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(long administratorId) {
        this.administratorId = administratorId;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", administratorId=" + administratorId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(name, activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
