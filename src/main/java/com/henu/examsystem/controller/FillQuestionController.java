package com.henu.examsystem.controller;

import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.entity.FillQuestion;
import com.henu.examsystem.serviceimpl.FillQuestionServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FillQuestionController {
    @Autowired
    FillQuestionServiceImpl fillQuestionService;
    @GetMapping("/paperFill/{id}")
    public ApiResult paperFill(@PathVariable Integer id){
        List<FillQuestion> fillQuestions = fillQuestionService.findByIdAndType(id);
        if (!fillQuestions.isEmpty())
        return ApiResultHandler.buildApiResult(200, "请求成功", fillQuestions);
        else return ApiResultHandler.buildApiResult(404, "请求失败", null);
    }
}
