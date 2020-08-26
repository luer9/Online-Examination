package com.henu.examsystem.controller;

import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.entity.Score;
import com.henu.examsystem.entity.Student;
import com.henu.examsystem.serviceimpl.ScoreServiceImpl;
import com.henu.examsystem.serviceimpl.StudentServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ScoreTableController {
    @Autowired
    private ScoreServiceImpl scoreService;
    @Autowired
    private StudentServiceImpl studentService;


    @GetMapping("/find_scoreTable")
    public ApiResult findScoreTable(HttpSession session){
        Student student = (Student)session.getAttribute("loguser");
        Integer studentId = student.getStudentId();
        List<Score> score_list = scoreService.findById(studentId);
        List<Object> list = new ArrayList<>();
        list.add(student);
        list.add(score_list);
        return ApiResultHandler.buildApiResult(200, "请求成功", list);
    }

}
