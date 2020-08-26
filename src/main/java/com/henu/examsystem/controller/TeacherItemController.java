package com.henu.examsystem.controller;

import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.entity.Teacher;
import com.henu.examsystem.serviceimpl.TeacherServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class TeacherItemController {
    @Autowired TeacherServiceImpl teacherService;
    @GetMapping("/tea_show")
    public ApiResult teaShow(HttpSession session){
        Teacher tea = (Teacher) session.getAttribute("loguser");
        System.out.println(tea);
        return  ApiResultHandler.buildApiResult(200, "请求成功", tea);
    }
    @PostMapping("/editPwd")
    public ApiResult editPwd(@RequestParam("newpwd") String newpwd,
                             @RequestParam("oldpwd") String oldpwd,
                             HttpSession session){
        Teacher tea = (Teacher)  session.getAttribute("loguser");
        if (tea.getPwd().equals(oldpwd)){
            System.out.println(newpwd);
            tea.setPwd(newpwd);
            teacherService.update(tea);
            session.setAttribute("loguser",tea);
            return  ApiResultHandler.buildApiResult(200, "请求成功", tea);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);
    }
}
