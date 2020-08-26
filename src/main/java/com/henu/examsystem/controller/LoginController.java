package com.henu.examsystem.controller;

import com.henu.examsystem.entity.Admin;
import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.entity.Student;
import com.henu.examsystem.entity.Teacher;
import com.henu.examsystem.serviceimpl.LoginServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class LoginController {
        @Autowired
        private LoginServiceImpl loginService;

        @PostMapping(value = "/loginError")
        public ApiResult login(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("author") String author,
                               Map<String,Object> map, HttpSession session){
            if(author.equals("stu")){
                Student student = loginService.studentLogin(Integer.valueOf(username),password);
                if(student != null){
                    student.setPwd(password);
                    session.setAttribute("loguser",student);
                    return ApiResultHandler.buildApiResult(1, "请求成功", student);
                }else
                    return ApiResultHandler.buildApiResult(400, "请求失败", null);
            }
            if(author.equals("tea")){
                Teacher teacher = loginService.teacherLogin(Integer.valueOf(username),password);
                if(teacher != null){
                    teacher.setPwd(password);
                    session.setAttribute("loguser",teacher);
                    return ApiResultHandler.buildApiResult(2, "请求成功", teacher);
                }else
                    return ApiResultHandler.buildApiResult(400, "请求失败", null);
            }
            if(author.equals("admin")){
                Admin admin = loginService.adminLogin(Integer.valueOf(username),password);
                if(admin != null){
                    admin.setPwd(password);
                    session.setAttribute("loguser",admin);
                    return ApiResultHandler.buildApiResult(3, "请求成功", admin);
                }else
                    return ApiResultHandler.buildApiResult(400, "请求失败", null);
            }
//            if (StringUtils.isEmpty(name1)) {
//                map.put("msg","用户名密码错误");
//                return  "index";
//            }
            return ApiResultHandler.buildApiResult(404, "请求失败", null);
//            if(username.equals("admin") && "123456".equals(password)){
//                //登陆成功，防止表单重复提交，可以重定向到主页
//                session.setAttribute("loginUser",username);
//                return "redirect:/AdminPage/AdminIndex.html";
//            }else if (username.equals("tea") && "123456".equals(password)){
//                //登陆成功，防止表单重复提交，可以重定向到主页
//                session.setAttribute("loginUser",username);
//                return "redirect:/TeacherPage/Tea.html";
//            }else if (username.equals("stu") && "123456".equals(password)){
//                //登陆成功，防止表单重复提交，可以重定向到主页
//                session.setAttribute("loginUser",username);
//                return "redirect:/StudentPage/Stu.html";
//            }else{
//                //登陆失败
//
//                map.put("msg","用户名密码错误");
//                return  "1";
//            }

    }
}
