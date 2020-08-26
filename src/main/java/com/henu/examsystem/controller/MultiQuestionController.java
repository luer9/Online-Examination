package com.henu.examsystem.controller;

import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.entity.MultiQuestion;
import com.henu.examsystem.serviceimpl.MultiQuestionServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MultiQuestionController {
    @Autowired
    MultiQuestionServiceImpl multiQuestionService;
    @GetMapping("/paperMulti/{id}")
    public ApiResult paperMulti(@PathVariable Integer id){
        List<MultiQuestion> multiQuestions = multiQuestionService.findByIdAndType(id);
        System.out.println(multiQuestions);
        if (!multiQuestions.isEmpty()){
            return ApiResultHandler.buildApiResult(200, "请求成功", multiQuestions);
        }else return ApiResultHandler.buildApiResult(404, "请求失败", null);

    }
}
