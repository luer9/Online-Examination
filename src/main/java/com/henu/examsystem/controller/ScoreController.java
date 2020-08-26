package com.henu.examsystem.controller;

import com.henu.examsystem.entity.*;
import com.henu.examsystem.serviceimpl.*;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class ScoreController {
    @Autowired
    FillQuestionServiceImpl fillQuestionService;
    @Autowired
    MultiQuestionServiceImpl multiQuestionService;
    @Autowired
    JudgeQuestionServiceImpl judgeQuestionService;
    @Autowired
    ScoreServiceImpl scoreService;
    @Autowired
    StudentServiceImpl studentService;
    @PostMapping("/countScore")
    public ApiResult countScore(@RequestParam("multi[]") ArrayList<String> multi,
                                @RequestParam("fill[]") ArrayList<String> fill,
                                @RequestParam("judge[]") ArrayList<String> judge,
                                HttpSession session
                                ){
        ExamManage examManage = (ExamManage)session.getAttribute("examManage");
        Student student = (Student)session.getAttribute("loguser");
        int count = 0;
        for (String m : multi){
            String[] a = m.split("_");
            String answer = a[0];
            Integer questionId = Integer.parseInt(a[1]);
            MultiQuestion multiQuestion = multiQuestionService.findById(questionId);
            if(answer.equals(multiQuestion.getRightAnswer()))
                count = count + multiQuestion.getScore();
        }
        for (String f : fill){
             String[] a = f.split("_");
            String answer = a[0];
            String[] stuAns = answer.trim().split("\\s+");
            Integer questionId = Integer.parseInt(a[1]);
            FillQuestion fillQuestion = fillQuestionService.findById(questionId);
            String[] rightAns = fillQuestion.getAnswer().trim().split("\\s+"); //把答案空格分隔开
            int size = stuAns.length, trueSize = rightAns.length;
            int rightCnt = 0;
            for(int i = 0; i < size; i++){
                //一个答案的多种表示形式
                String[] singleAns = rightAns[i].trim().split("\\|");
                if(Arrays.asList(singleAns).contains(stuAns[i])){
                    rightCnt++;
                }
            }
            count += Math.ceil(fillQuestion.getScore()*1.0/trueSize*1.0*rightCnt*1.0);
        }
        for (String j : judge){
            String[] a = j.split("_");
            String answer = a[0];
            Integer questionId = Integer.parseInt(a[1]);
            JudgeQuestion judgeQuestion = judgeQuestionService.findById(questionId);
            if (answer.equals(judgeQuestion.getAnswer()))
                count = count+judgeQuestion.getScore();
        }
        Score score = new Score();
//        examCode,studentId,subject,ptScore,etScore,score,answerDate
        score.setExamCode(examManage.getExamCode());
        score.setSubject(examManage.getSource());
        score.setStudentId(student.getStudentId());
        score.setPtScore(null);
        score.setEtScore(count);
        score.setScore(null);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Date nowtime=new Date();
        String strTime=df.format(nowtime);
        score.setAnswerDate(strTime);
        System.out.println(score);
        scoreService.add(score);
        session.setAttribute("score",score);
        return ApiResultHandler.buildApiResult(200,"请求成功",score);
    }
    @GetMapping("/paperEnd")
    public ApiResult paperEnd(HttpSession session){
        Score score = (Score)session.getAttribute("score");
        if (score!=null){
            return ApiResultHandler.buildApiResult(200,"请求成功",score);
        }else return ApiResultHandler.buildApiResult(404,"请求失败",null);


    }
    @GetMapping("/paperE")
    public ApiResult paperE(HttpSession session){
        ExamManage examManage = (ExamManage)session.getAttribute("examManage");
        if(examManage!=null){
            return ApiResultHandler.buildApiResult(200,"请求成功",examManage);
        }else return ApiResultHandler.buildApiResult(404,"请求失败",null);
    }
    @GetMapping("/paperEn")
    public ApiResult paperEn(HttpSession session){
        Student student = (Student)session.getAttribute("loguser");
        if (student!=null){
            return ApiResultHandler.buildApiResult(200,"请求成功",student);
        }else return ApiResultHandler.buildApiResult(404,"请求失败",null);
    }

    @GetMapping("/find_score/{id}")
    public ApiResult find_score(@PathVariable("id") Integer id){
        Student student = studentService.findById(id);
        List<Score> score_list = scoreService.findById(id);
        List<Object> list = new ArrayList<>();
        list.add(student);
        list.add(score_list);
        return ApiResultHandler.buildApiResult(200, "请求成功", list);
    }

    @GetMapping("/scoreEdit/{id}")
    public  ApiResult scoreEdit(@PathVariable("id") Integer id){
        System.out.println("_-------------------------------");
        System.out.println(id);

        Student student = studentService.findById(id);
        List<Score> score_list = scoreService.findById(id);
        List<Object> list = new ArrayList<>();
        list.add(student);
        list.add(score_list);
        return ApiResultHandler.buildApiResult(200, "请求成功", list);
    }

    @GetMapping("/del_score/{id}")
    public ApiResult delExam(@PathVariable("id") Integer id){
        int result = scoreService.deleteById(id);
        if (result==1){
            return ApiResultHandler.buildApiResult(200, "请求成功", null);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);

    }

//    @PostMapping("/score_saveEdit")
//    public ApiResult save(@RequestParam Map<String, Object> params ){
//
//        String scoreId = (String) params.get("scoreId");
//        Score score = scoreService.findByScoreId(Integer.valueOf(scoreId));
//        score.setEtScore((Integer)params.get("etScore"));
//
//        int result = scoreService.update(teacher);
//        if (result==1){
//            System.out.println("修改为:" + teacher);
//            return ApiResultHandler.buildApiResult(200, "请求成功", teacher);
//        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);
//    }
}
