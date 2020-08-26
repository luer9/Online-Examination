package com.henu.examsystem.controller;

import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.entity.JudgeQuestion;
import com.henu.examsystem.serviceimpl.JudgeQuestionServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JudgeQuestionController {
    @Autowired
    JudgeQuestionServiceImpl judgeQuestionService;
    @GetMapping("/paperJudge/{id}")
    public ApiResult paperFill(@PathVariable Integer id){
        List<JudgeQuestion> judgeQuestions = judgeQuestionService.findByIdAndType(id);
        if (!judgeQuestions.isEmpty())
            return ApiResultHandler.buildApiResult(200, "请求成功", judgeQuestions);
        else return ApiResultHandler.buildApiResult(404, "请求失败", null);
    }
}
