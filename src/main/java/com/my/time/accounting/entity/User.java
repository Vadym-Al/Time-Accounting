package com.my.time.accounting.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class User {
    private long userId;
    private String name;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private String company;
    private Timestamp createTime;
    private long teamId;
    private long administratorId;

    public static User createUser(String name, String lastName, String password, String email, String phoneNumber, String company, long teamId, long administratorId){
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setCompany(company);
        user.setTeamId(teamId);
        user.setAdministratorId(administratorId);
        return user;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(long administratorId) {
        this.administratorId = administratorId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", company='" + company + '\'' +
                ", createTime=" + createTime +
                ", teamId=" + teamId +
                ", administratorId=" + administratorId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return password.equals(user.password) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, email);
    }
}
