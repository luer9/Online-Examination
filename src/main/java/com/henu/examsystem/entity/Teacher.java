package com.henu.examsystem.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class Teacher {
    private Integer teacherId;

    private String teacherName;

    private String institute;

    private String sex;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;

    private String type;

    private String role;

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", institute='" + institute + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", cardId='" + cardId + '\'' +
                ", type='" + type + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getTeacherId(), teacher.getTeacherId()) &&
                Objects.equals(getTeacherName(), teacher.getTeacherName()) &&
                Objects.equals(getInstitute(), teacher.getInstitute()) &&
                Objects.equals(getSex(), teacher.getSex()) &&
                Objects.equals(getTel(), teacher.getTel()) &&
                Objects.equals(getEmail(), teacher.getEmail()) &&
                Objects.equals(getPwd(), teacher.getPwd()) &&
                Objects.equals(getCardId(), teacher.getCardId()) &&
                Objects.equals(getType(), teacher.getType()) &&
                Objects.equals(getRole(), teacher.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeacherId(), getTeacherName(), getInstitute(), getSex(), getTel(), getEmail(), getPwd(), getCardId(), getType(), getRole());
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}