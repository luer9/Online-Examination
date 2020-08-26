package com.henu.examsystem.service;

import com.henu.examsystem.entity.Admin;
import com.henu.examsystem.entity.Student;
import com.henu.examsystem.entity.Teacher;

public interface LoginService {

    public Admin adminLogin(Integer username, String password);

    public Teacher teacherLogin(Integer username, String password);

    public Student studentLogin(Integer username, String password);
}
