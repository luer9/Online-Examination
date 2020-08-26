package com.henu.examsystem.serviceimpl;

import com.henu.examsystem.entity.Admin;
import com.henu.examsystem.entity.Student;
import com.henu.examsystem.entity.Teacher;
import com.henu.examsystem.mapper.LoginMapper;
import com.henu.examsystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired(required = false)
    private LoginMapper loginMapper;

    @Override
    public Admin adminLogin(Integer username, String password) {
        return loginMapper.adminLogin(username,password);
    }

    @Override
    public Teacher teacherLogin(Integer username, String password) {
        return loginMapper.teacherLogin(username,password);
    }

    @Override
    public Student studentLogin(Integer username, String password) {
        return loginMapper.studentLogin(username,password);
    }
}
