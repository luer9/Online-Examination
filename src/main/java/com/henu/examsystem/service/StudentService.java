package com.henu.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henu.examsystem.entity.Student;

import java.util.List;

public interface StudentService {

    IPage<Student> findAll(Page<Student> page);

    public List<Student> findAllPage();

    Student findById(Integer studentId);

    int findByName(String studentName);

    int deleteById(Integer studentId);

    int update(Student student);

    int updatePwd(Student student);
    int add(Student student);
}
