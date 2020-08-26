package com.henu.examsystem.controller;

import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.entity.Student;
import com.henu.examsystem.serviceimpl.StudentServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import com.henu.examsystem.util.ImportStudentExcelUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    ImportStudentExcelUtil importStudentExcelUtil;
    @Autowired
    StudentServiceImpl studentService;
    @PostMapping("/add_stu")
    public ApiResult addStudent(@NotNull @RequestParam("student_file") MultipartFile file ){

        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = importStudentExcelUtil.batchImport(fileName,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (a==true){
            return ApiResultHandler.buildApiResult(200, "请求成功", null);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);

    }
    @PostMapping("/addStudent")
    public ApiResult addStudent(@RequestBody Student student){
        System.out.println("插入"+student);
        studentService.add(student);
        return ApiResultHandler.buildApiResult(200, "请求成功", null);
    }
    @GetMapping("/stu_search")
    public ApiResult stuSearch(Model model){
        List<Student> students = studentService.findAllPage();

        return ApiResultHandler.buildApiResult(200, "请求成功", students);
    }
    @GetMapping("/stuEdit/{id}")
    public  ApiResult examEdit(@PathVariable("id") Integer id){
        Student student = studentService.findById(id);
        return ApiResultHandler.buildApiResult(200, "请求成功", student);
    }

    @PostMapping("/student_saveEdit")
    public ApiResult save(@RequestParam Map<String, Object> params ){

        String studentId = (String) params.get("studentId");
        Student student = studentService.findById(Integer.valueOf(studentId));

        student.setStudentName((String)params.get("studentName"));
        student.setSex((String) params.get("sex"));
        student.setInstitute((String) params.get("institute"));
        student.setGrade((String) params.get("grade"));
        student.setClazz((String) params.get("clazz"));
        student.setTel((String) params.get("tel"));
        student.setCardId((String) params.get("cardId"));
        student.setEmail((String) params.get("email"));
        student.setPwd((String) params.get("pwd"));

        int result = studentService.update(student);
        if (result==1){
            System.out.println("修改为:" + student);
            return ApiResultHandler.buildApiResult(200, "请求成功", student);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);
        /**/
    }
    @GetMapping("/del_stu/{id}")
    public ApiResult delExam(@PathVariable("id") Integer id){
        int result = studentService.deleteById(id);
        if (result==1){
            return ApiResultHandler.buildApiResult(200, "请求成功", null);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);
    }
    @GetMapping("/find_stu/{id}")
    public ApiResult findStudent(@PathVariable("id") Integer id){
        System.out.println(id);
        Student student = studentService.findById(id);
        return ApiResultHandler.buildApiResult(200, "请求成功", student);
    }
    @PostMapping(value= "/ResearcherServlet")
    public ApiResult updatePwd(@RequestParam("newpwd") String pwd,
                               HttpSession session) {

        Student student = (Student)session.getAttribute("loguser");

        student.setPwd(pwd);
        studentService.updatePwd(student);
        return ApiResultHandler.buildApiResult(1,"密码更新成功",null);
    }
    @GetMapping("/portfolio")
    public ApiResult portfolio(HttpSession session){
        Student student = (Student) session.getAttribute("loguser");

        return ApiResultHandler.buildApiResult(200,"请求成功",student);
    }
}
