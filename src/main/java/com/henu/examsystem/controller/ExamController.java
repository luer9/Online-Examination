package com.henu.examsystem.controller;

import com.henu.examsystem.entity.*;
import com.henu.examsystem.serviceimpl.*;
import com.henu.examsystem.util.ApiResultHandler;
import com.henu.examsystem.util.ImportExamExcelUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * author: yll
 * */
@RestController
public class ExamController {
    @Autowired
    ImportExamExcelUtil importExamExcelUtil;
    @Autowired
    FillQuestionServiceImpl fillQuesService;
    @Autowired
    JudgeQuestionServiceImpl judgeQuesService;
    @Autowired
    MultiQuestionServiceImpl multiQuesService;
    @Autowired
    ExamManageServiceImpl examManageService;
    @Autowired
    PaperManageServiceImpl paperManageService;
    @PostMapping("/add_exam")
    public ApiResult addExam(@NotNull @RequestParam("exam_file") MultipartFile file ){

        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = importExamExcelUtil.batchImport(fileName,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (a==true){
            return ApiResultHandler.buildApiResult(200, "请求成功", null);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);

    }
    @GetMapping("/fillExam_search")
    public ApiResult fillexamSearch(Model model){
//        List<Teacher> teachers = teacherService.findAllPage();
        List<FillQuestion> fillQuestions = fillQuesService.findAllPage();
        return ApiResultHandler.buildApiResult(200, "请求成功", fillQuestions);

    }
    @PostMapping("/addItem")
    public ApiResult addItem(@RequestBody ExamManage examManage){

        System.out.println("插入"+examManage);
        examManageService.add(examManage);
        return ApiResultHandler.buildApiResult(200, "请求成功", null);
    }
    @GetMapping("/judgeExam_search")
    public ApiResult judgeexamSearch(Model model){
//        List<Teacher> teachers = teacherService.findAllPage();
        List<JudgeQuestion> judgeQuestions = judgeQuesService.findAllPage();
        return ApiResultHandler.buildApiResult(200, "请求成功", judgeQuestions);
    }
    @GetMapping("/multiExam_search")
    public ApiResult multiexamSearch(Model model){
//        List<Teacher> teachers = teacherService.findAllPage();
        List<MultiQuestion> multiQuestions = multiQuesService.findAllPage();
        return ApiResultHandler.buildApiResult(200, "请求成功", multiQuestions);
    }


    @GetMapping("/exam_search")
    public ApiResult examSearch(Model model){
//        List<Teacher> teachers = teacherService.findAllPage();
        List<ExamManage> examLists = examManageService.findAll();
        return ApiResultHandler.buildApiResult(200, "请求成功", examLists);
    }

//    @GetMapping("/find_tea/{id}")
//    public ApiResult findTeacher(@PathVariable("id") Integer id){
//        System.out.println(id);
//        Teacher teacher = teacherService.findById(id);
//        return ApiResultHandler.buildApiResult(200, "请求成功", teacher);
//    }
    //试卷题目查找
    @GetMapping("/showFillQues/{id}")
    public  ApiResult showFillQues(@PathVariable("id") Integer id){
        List<PaperManage> PaperManages = paperManageService.findById(2,id);
        List<FillQuestion> fillQuestions = new ArrayList<FillQuestion>();
        for (int i = 0; i <PaperManages.size() ; i++) {
            Integer QuestionId = PaperManages.get(i).getQuestionId();
            FillQuestion fillQuestion = fillQuesService.findById(QuestionId);
            System.out.println(fillQuestion);
            fillQuestions.add(fillQuestion);
        }

        return ApiResultHandler.buildApiResult(200, "请求成功", fillQuestions);
    }
    @GetMapping("/showJudgeQues/{id}")
    public  ApiResult showJudgeQues(@PathVariable("id") Integer id){
        List<PaperManage> PaperManages = paperManageService.findById(3,id);
        List<JudgeQuestion> judgeQuestions = new ArrayList<JudgeQuestion>();
        for (int i = 0; i <PaperManages.size() ; i++) {
            Integer QuestionId = PaperManages.get(i).getQuestionId();
            JudgeQuestion JudgeQuestion = judgeQuesService.findById(QuestionId);
            System.out.println(JudgeQuestion);
            judgeQuestions.add(JudgeQuestion);
        }
        return ApiResultHandler.buildApiResult(200, "请求成功", judgeQuestions);
    }
    @GetMapping("/showMultiQues/{id}")
    public  ApiResult showMultiQues(@PathVariable("id") Integer id){
        List<PaperManage> PaperManages = paperManageService.findById(1,id);
        List<MultiQuestion> multiQuestions = new ArrayList<MultiQuestion>();
        for (int i = 0; i <PaperManages.size() ; i++) {
            Integer QuestionId = PaperManages.get(i).getQuestionId();
            MultiQuestion multiQuestion = multiQuesService.findById(QuestionId);
            System.out.println(multiQuestion);
            multiQuestions.add(multiQuestion);
        }
        return ApiResultHandler.buildApiResult(200, "请求成功", multiQuestions);
    }
    @GetMapping("/getExam/{id}")
    public ApiResult getExam(@PathVariable("id") Integer id){
        ExamManage exam = examManageService.findByPaperId(id);
        return ApiResultHandler.buildApiResult(200, "请求成功",exam );
    }
    @PostMapping("/searchQuestion")
    public ApiResult searchQuestion(@RequestParam("section") String section,
                                    @RequestParam("type") String type,
                                    @RequestParam("level") String level,
                                    @RequestParam("score") String score){
        System.out.println(section+" "+type+" "+level+" "+score);
        int score1 = Integer.parseInt(score);
        List list = new ArrayList();
        if(type.equals("选择题")){
            List<MultiQuestion> multiQuestions = multiQuesService.findAllPage();
            for (MultiQuestion multiQuestion:multiQuestions) {
                String s = multiQuestion.getSection();
                String l = multiQuestion.getLevel();
                Integer s1 = multiQuestion.getScore();
                if(s.equals(section)&&l.equals(level)&&s1==score1)
                    list.add(multiQuestion);
            }
           return ApiResultHandler.buildApiResult(1, "请求成功",list );
        }else if (type.equals("填空题")){
            List<FillQuestion> fillQuestions = fillQuesService.findAllPage();
            for (FillQuestion fillQuestion:fillQuestions) {
                if(fillQuestion.getSection().equals(section)&&fillQuestion.getLevel().equals(level)&&fillQuestion.getScore().equals(score))
                    list.add(fillQuestion);
            }
            return ApiResultHandler.buildApiResult(2, "请求成功",list );
        }else if(type.equals("判断题")){
            List<JudgeQuestion> judgeQuestions = judgeQuesService.findAllPage();
            for (JudgeQuestion JudgeQuestion:judgeQuestions) {
                if(JudgeQuestion.getSection().equals(section)&&JudgeQuestion.getLevel().equals(level)&&JudgeQuestion.getScore().equals(score))
                    list.add(JudgeQuestion);
            }
            return ApiResultHandler.buildApiResult(3, "请求成功",list );
        }else return ApiResultHandler.buildApiResult(500, "请求失败",null );

    }
}
