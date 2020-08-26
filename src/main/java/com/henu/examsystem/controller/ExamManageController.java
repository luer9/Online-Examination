package com.henu.examsystem.controller;

import com.henu.examsystem.entity.*;
import com.henu.examsystem.serviceimpl.*;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ExamManageController {
    @Autowired
    private ExamManageServiceImpl examManageService;
    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;
    @Autowired
    private FillQuestionServiceImpl fillQuestionService;
    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;
    @Autowired
    private PaperManageServiceImpl paperManageService;
    //查询所有考试并返回
    @GetMapping("/examSearch")
    public ApiResult searchExam(Model model){
        System.out.println("不分页查询所有试卷");
        List<ExamManage> examManages = examManageService.findAll();
        //将li放到请求域中
//        model.addAttribute("examManages",examManages);
        return ApiResultHandler.buildApiResult(200, "请求成功", examManages);
    }
    @GetMapping("/examEdit/{id}")
    public  ApiResult examEdit(@PathVariable("id") Integer id){
        ExamManage examManage = examManageService.findById(id);
        return ApiResultHandler.buildApiResult(200, "请求成功", examManage);
    }
    @PostMapping("/exam_saveEdit")
    public ApiResult save( @RequestParam Map<String, Object> params ){
        String examCode = (String) params.get("examCode");
        String totalTime = (String) params.get("totalTime");
        String totalScore = (String) params.get("totalScore");
        System.out.println(examCode);
        ExamManage examManage = examManageService.findById(Integer.valueOf(examCode));
        examManage.setSource((String) params.get("source"));
        examManage.setDescription((String) params.get("description"));
        examManage.setInstitute((String) params.get("institute"));
        examManage.setMajor((String) params.get("major"));
        examManage.setGrade((String) params.get("grade"));
        examManage.setExamDate((String) params.get("examDate"));
        examManage.setTotalTime(Integer.valueOf(totalTime));
        examManage.setTotalScore(Integer.valueOf(totalScore));
        int result = examManageService.update(examManage);
        if (result==1){
            System.out.println("修改为:" + examManage);
            return ApiResultHandler.buildApiResult(200, "请求成功", examManage);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);

    }
    @GetMapping("/delExam/{id}")
    public ApiResult delExam(@PathVariable("id") Integer id){
        int result = examManageService.delete(id);
        if (result==1){
            return ApiResultHandler.buildApiResult(200, "请求成功", null);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);

    }

    @GetMapping("/searchExam/{id}")
    public  ApiResult searchExam(@PathVariable("id") Integer id){
        ExamManage examManage = examManageService.findById(id);
        return ApiResultHandler.buildApiResult(200, "请求成功", examManage);
    }
    @GetMapping("/exam_sea")
    public ApiResult exam_search(){
        List<MultiQuestion> multiQuestions = multiQuestionService.findAllPage();

        List<JudgeQuestion> judgeQuestions = judgeQuestionService.findAllPage();
        List<FillQuestion>  fillQuestions  = fillQuestionService.findAllPage();



        List<Object> list = new ArrayList<>();
        list.add(multiQuestions);
        list.add(judgeQuestions);
        list.add(fillQuestions);
        return ApiResultHandler.buildApiResult(200, "请求成功", list);
    }
    @PostMapping("/examAddOne/{questionId}/{questionType}/{examCode}")
    public ApiResult examAddOne(@PathVariable("questionId") Integer questionId,
                                @PathVariable("questionType") Integer questionType,
                                @PathVariable("examCode") Integer examCode){
        ExamManage examManage = examManageService.findById(examCode);
        Integer paperId=examManage.getPaperId();
        int flag = paperManageService.isExist(paperId,questionType,questionId);//返回1则有这条记录返回0则没有;
        List<PaperManage> paperManages = new ArrayList<PaperManage>();
        PaperManage paperManage = new PaperManage();
        paperManage.setPaperId(paperId);
        paperManage.setQuestionType(questionType);
        paperManage.setQuestionId(questionId);
        paperManages = paperManageService.findByIdd(paperId);
        int one=0;
        int two=0;
        int three=0;
        for (PaperManage p:paperManages) {
            if(p.getQuestionType()==1)
            one++;
            if(p.getQuestionType()==2)
                two++;
            if(p.getQuestionType()==3)
                three++;

        }


        System.out.println(one);
        System.out.println(two);
        System.out.println(three);
        if (flag==0){
            if (questionType==1&&one<20){
                paperManageService.add(paperId,questionType,questionId);
                return ApiResultHandler.buildApiResult(200, "请求成功", paperManage);
            }else if (questionType==2&&two<10){
                paperManageService.add(paperId,questionType,questionId);
                return ApiResultHandler.buildApiResult(200, "请求成功", paperManage);
            }else if (questionType==3&&three<10){
                paperManageService.add(paperId,questionType,questionId);
                return ApiResultHandler.buildApiResult(200, "请求成功", paperManage);
            }else return ApiResultHandler.buildApiResult(500, "请求失败", null);


        }else return ApiResultHandler.buildApiResult(404, "请求成功", null);
    }
}
