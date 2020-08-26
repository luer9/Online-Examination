package com.henu.examsystem.controller;

import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.entity.Teacher;
import com.henu.examsystem.serviceimpl.TeacherServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import com.henu.examsystem.util.ImportTeacherExcelUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class TeacherController {
    @Autowired
    ImportTeacherExcelUtil importTeacherExcelUtil;
    @Autowired
    TeacherServiceImpl teacherService;
    @PostMapping("/add_tea")
    public ApiResult addTeacher(@NotNull @RequestParam("teacher_file") MultipartFile file ){

        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = importTeacherExcelUtil.batchImport(fileName,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (a==true){
            return ApiResultHandler.buildApiResult(200, "请求成功", null);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);

    }
    @PostMapping("/addTeacher")
    public ApiResult addTeacher(@RequestBody Teacher teacher){

        System.out.println("插入"+teacher);
        teacherService.add(teacher);
        return ApiResultHandler.buildApiResult(200, "请求成功", null);
    }
    @GetMapping("/tea_search")
    public ApiResult teaSearch(Model model){
        List<Teacher> teachers = teacherService.findAllPage();

        return ApiResultHandler.buildApiResult(200, "请求成功", teachers);
    }
    @GetMapping("/teaEdit/{id}")
    public  ApiResult examEdit(@PathVariable("id") Integer id){
        Teacher teacher = teacherService.findById(id);
        return ApiResultHandler.buildApiResult(200, "请求成功", teacher);
    }
    @PostMapping("/teacher_saveEdit")
    public ApiResult save(@RequestParam Map<String, Object> params ){


        String teacherId = (String) params.get("teacherId");
        Teacher teacher = teacherService.findById(Integer.valueOf(teacherId));
        teacher.setTeacherName((String)params.get("teacherName"));
        teacher.setInstitute((String) params.get("institute"));
        teacher.setSex((String) params.get("sex"));
        teacher.setTel((String) params.get("tel"));
        teacher.setPwd((String) params.get("pwd"));
        teacher.setCardId((String) params.get("cardId"));
        int result = teacherService.update(teacher);
        if (result==1){
            System.out.println("修改为:" + teacher);
            return ApiResultHandler.buildApiResult(200, "请求成功", teacher);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);

    }
    @GetMapping("/del_tea/{id}")
    public ApiResult delExam(@PathVariable("id") Integer id){
        int result = teacherService.deleteById(id);
        if (result==1){
            return ApiResultHandler.buildApiResult(200, "请求成功", null);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);

    }
    @GetMapping("/find_tea/{id}")
    public ApiResult findTeacher(@PathVariable("id") Integer id){
        System.out.println(id);
        Teacher teacher = teacherService.findById(id);
        return ApiResultHandler.buildApiResult(200, "请求成功", teacher);
    }
}
