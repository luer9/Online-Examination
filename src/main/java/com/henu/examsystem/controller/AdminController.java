package com.henu.examsystem.controller;

import com.henu.examsystem.entity.Admin;
import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.serviceimpl.AdminServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;
    @GetMapping("/admin_show")
    public ApiResult adminShow(HttpSession session){
        Admin admin = (Admin) session.getAttribute("loguser");
        System.out.println(admin);
        return  ApiResultHandler.buildApiResult(200, "请求成功", admin);
    }
    @PostMapping("/edit_pwd")
    public ApiResult editPwd(@RequestParam("newpwd") String newpwd,
                             @RequestParam("oldpwd") String oldpwd,
                             HttpSession session){

        Admin admin = (Admin)  session.getAttribute("loguser");
        System.out.println(oldpwd);
        System.out.println(admin.getPwd());
        if (admin.getPwd().equals(oldpwd)){
            System.out.println(newpwd);
            admin.setPwd(newpwd);
            adminService.update(admin);
            session.setAttribute("loguser",admin);
            return  ApiResultHandler.buildApiResult(200, "请求成功", admin);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);

    }
    @PostMapping("/admin_logout")
    public ApiResult adminLogout(HttpSession session){
        session.removeAttribute("loguser");
        return ApiResultHandler.buildApiResult(200, "请求成功", null);
    }
}