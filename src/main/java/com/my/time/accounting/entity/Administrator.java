package com.my.time.accounting.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Administrator {
    private long adminId;
    private String name;
    private String lastName;
    private String password;
    private String email;
    private String company;
    private String phoneNumber;
    private Timestamp createTime;

    public static Administrator createAdministrator(String name, String lastName, String password, String email, String company, String phoneNumber){
        Administrator administrator = new Administrator();
        administrator.setName(name);
        administrator.setLastName(lastName);
        administrator.setPassword(password);
        administrator.setEmail(email);
        administrator.setCompany(company);
        administrator.setPhoneNumber(phoneNumber);
        return administrator;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "admin_id=" + adminId +
                ", name='" + name + '\'' +
                ", last_name='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", phone_number=" + phoneNumber +
                ", create_time=" + createTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return password.equals(that.password) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, email);
    }
}
