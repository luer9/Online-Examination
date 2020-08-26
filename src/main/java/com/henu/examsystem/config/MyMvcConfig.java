package com.henu.examsystem.config;

import com.henu.examsystem.component.LoginHandlerInterceptor;
import com.henu.examsystem.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        浏览器发送/login请求来带index页面
        registry.addViewController("/login").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/logout.html").setViewName("index");
        registry.addViewController("/Admin.html").setViewName("AdminPage/AdminIndex");
        registry.addViewController("/Tea.html").setViewName("TeacherPage/TeacherIndex");
        registry.addViewController("/Stu.html").setViewName("StudentPage/studentindex");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/loginError").setViewName("index");
//        以下是管理员界面的ViewController
        registry.addViewController("/AdminIndex.html").setViewName("AdminPage/AdminIndex");
        registry.addViewController("/ItemIntro.html").setViewName("AdminPage/ItemIntro");
        registry.addViewController("/AddItem.html").setViewName("AdminPage/AddItem");
        registry.addViewController("/ExamIntro.html").setViewName("AdminPage/ExamIntro");
        registry.addViewController("/ExamAdmin.html").setViewName("AdminPage/ExamAdmin");
        registry.addViewController("/AddExam.html").setViewName("AdminPage/AddExam");
        registry.addViewController("/StuGradeSearch.html").setViewName("AdminPage/StuGradeSearch");
        registry.addViewController("/StuFenGradeSearch.html").setViewName("AdminPage/StuFenGradeSearch");
        registry.addViewController("/StuAdmin.html").setViewName("AdminPage/StuAdmin");
        registry.addViewController("/AddStu.html").setViewName("AdminPage/AddStu");
        registry.addViewController("/TeachAdmin.html").setViewName("AdminPage/TeachAdmin");
        registry.addViewController("/AddTeach.html").setViewName("AdminPage/AddTeach");
        registry.addViewController("/ExamSearch.html").setViewName("AdminPage/ExamSearch");
        registry.addViewController("/AdminItem.html").setViewName("AdminPage/AdminItem");
        registry.addViewController("/addTest.html").setViewName("AdminPage/addTest");
        registry.addViewController("/searchtest.html").setViewName("AdminPage/searchtest");
        registry.addViewController("/ComposeTest.html").setViewName("AdminPage/ComposeTest");

        //学生端
        registry.addViewController("/ExamMsg.html").setViewName("StudentPage/ExamMsg");
        registry.addViewController("/solve.html").setViewName("StudentPage/solve");
        registry.addViewController("/studentScore.html").setViewName("StudentPage/studentScore");
        registry.addViewController("/scoreTable.html").setViewName("StudentPage/scoreTable");
        registry.addViewController("/studentindex.html").setViewName("StudentPage/studentindex");
        registry.addViewController("/portfolio.html").setViewName("StudentPage/portfolio");
        //教师端
        registry.addViewController("/TeachItem.html").setViewName("TeacherPage/TeachItem");
        registry.addViewController("/TItemIntro.html").setViewName("TeacherPage/TItemIntro");
        registry.addViewController("/TExamSearch.html").setViewName("TeacherPage/TExamSearch");
        registry.addViewController("/TAddItem.html").setViewName("TeacherPage/TAddItem");
        registry.addViewController("/TExamAdmin.html").setViewName("TeacherPage/TExamAdmin");
        registry.addViewController("/TExamIntro.html").setViewName("TeacherPage/TExamIntro");
        registry.addViewController("/TAddExam.html").setViewName("TeacherPage/TAddExam");

        registry.addViewController("/TStuGradeSearch.html").setViewName("TeacherPage/TStuGradeSearch");
        registry.addViewController("/Taddtest.html").setViewName("TeacherPage/Taddtest");
        registry.addViewController("/TComposeTest.html").setViewName("TeacherPage/TComposeTest");
        registry.addViewController("/TStuFenGradeSearch.html").setViewName("TeacherPage/TStuFenGradeSearch");
        registry.addViewController("/Tsearchtest.html").setViewName("TeacherPage/Tsearchtest");
        //公共页面
        //admin
        registry.addViewController("/adminBar.html").setViewName("AdminPage/adminBar");
        registry.addViewController("/adminFooter.html").setViewName("AdminPage/adminFooter");
        registry.addViewController("/adminSlideBar.html").setViewName("AdminPage/adminSlideBar");

//        //teacher
        registry.addViewController("/teacherBar.html").setViewName("TeacherPage/teacherBar");
        registry.addViewController("/teacherFooter.html").setViewName("TeacherPage/teacherFooter");
        registry.addViewController("/teacherSlideBar.html").setViewName("TeacherPage/teacherSlideBar");

        //stu
        registry.addViewController("/stuFooter.html").setViewName("StudentPage/stuFooter");
        registry.addViewController("/stuNav.html").setViewName("StudentPage/stuNav");

         }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css");
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login","/loginError","/index.html","/index","/assets/**","/logout.html");

    }
//将国际化类加入容器中
    @Bean
    public LocaleResolver localeResolver(){

        return new MyLocaleResolver();
    }
}
