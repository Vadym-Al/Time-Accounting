package com.my.time.accounting.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class Task {
    private long taskId;
    private String name;
    private Date deadline;
    private String about;
    private Time wastedTime;
    private Timestamp createTime;
    private long activityType;
    private long administratorId;

    public static Task createTask(String name, Date deadline, String about, long activityType, long administratorId){
        Task task = new Task();
        task.setName(name);
        task.setDeadline(deadline);
        task.setAbout(about);
        task.setActivityType(activityType);
        task.setAdministratorId(administratorId);
        return task;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Time getWastedTime() {
        return wastedTime;
    }

    public void setWastedTime(Time wastedTime) {
        this.wastedTime = wastedTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public long getActivityType() {
        return activityType;
    }

    public void setActivityType(long activityType) {
        this.activityType = activityType;
    }

    public long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(long administratorId) {
        this.administratorId = administratorId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", deadline=" + deadline +
                ", about='" + about + '\'' +
                ", wastedTime=" + wastedTime +
                ", createTime=" + createTime +
                ", activityType=" + activityType +
                ", administratorId=" + administratorId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return activityType == task.activityType && name.equals(task.name) && deadline.equals(task.deadline) && Objects.equals(about, task.about) && Objects.equals(wastedTime, task.wastedTime) && createTime.equals(task.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, deadline, about, wastedTime, createTime, activityType);
    }
}
