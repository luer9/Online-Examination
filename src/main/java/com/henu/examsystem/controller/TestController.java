package com.henu.examsystem.controller;

import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.entity.FillQuestion;
import com.henu.examsystem.entity.JudgeQuestion;
import com.henu.examsystem.entity.MultiQuestion;
import com.henu.examsystem.serviceimpl.FillQuestionServiceImpl;
import com.henu.examsystem.serviceimpl.JudgeQuestionServiceImpl;
import com.henu.examsystem.serviceimpl.MultiQuestionServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    @Autowired
    MultiQuestionServiceImpl multiQuestionService;
    @Autowired
    FillQuestionServiceImpl fillQuestionService;
    @Autowired
    JudgeQuestionServiceImpl judgeQuestionService;
    @PostMapping("/add_Test")
    public ApiResult addTest(@RequestBody Map<String,String> parameter){
        String subject = parameter.get("source");
        String type = parameter.get("type");
        String section = parameter.get("section");
        String level = parameter.get("level");
        String rightAnswer = parameter.get("rightAnswer");
        String question = parameter.get("question");
        Integer score = Integer.valueOf(parameter.get("score"));
        String answerA = parameter.get("answerA");
        String answerB = parameter.get("answerB");
        String answerC = parameter.get("answerC");
        String answerD = parameter.get("answerD");
        String analysis = parameter.get("analysis");

        if (type.equals("1")){
            MultiQuestion multiQuestion = new MultiQuestion();
            multiQuestion.setSubject(subject);
            multiQuestion.setSection(section);
            multiQuestion.setLevel(level);
            multiQuestion.setRightAnswer(rightAnswer);
            multiQuestion.setQuestion(question);
            multiQuestion.setScore(score);
            multiQuestion.setAnswerA(answerA);
            multiQuestion.setAnswerB(answerB);
            multiQuestion.setAnswerC(answerC);
            multiQuestion.setAnswerD(answerD);
            multiQuestion.setAnalysis(analysis);
            int result = multiQuestionService.add(multiQuestion);
            if (result==1){
                return ApiResultHandler.buildApiResult(200,"请求成功",multiQuestion);
            }else return ApiResultHandler.buildApiResult(404,"请求失败",null);

        }else if (type.equals("2")){
            FillQuestion fillQuestion = new FillQuestion();
            fillQuestion.setSubject(subject);
            fillQuestion.setSection(section);
            fillQuestion.setLevel(level);
            fillQuestion.setAnswer(answerA);
            fillQuestion.setQuestion(question);
            fillQuestion.setScore(score);
            fillQuestion.setAnalysis(analysis);

            int result = fillQuestionService.add(fillQuestion);
            if (result==1){
                return ApiResultHandler.buildApiResult(200,"请求成功",null);
            }else return ApiResultHandler.buildApiResult(404,"请求失败",null);

        }else if (type.equals("3")){
            JudgeQuestion judgeQuestion = new JudgeQuestion();
            judgeQuestion.setSubject(subject);
            judgeQuestion.setSection(section);
            judgeQuestion.setLevel(level);
            judgeQuestion.setAnswer(answerA);
            judgeQuestion.setQuestion(question);
            judgeQuestion.setScore(score);
            judgeQuestion.setAnalysis(analysis);
            int result = judgeQuestionService.add(judgeQuestion);
            if (result==1){
                return ApiResultHandler.buildApiResult(200,"请求成功",null);
            }else return ApiResultHandler.buildApiResult(404,"请求失败",null);

        }else return ApiResultHandler.buildApiResult(404,"请求失败",null);

    }
}
