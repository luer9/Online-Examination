package com.henu.examsystem.controller;

import com.henu.examsystem.entity.ApiResult;
import com.henu.examsystem.entity.ExamManage;
import com.henu.examsystem.serviceimpl.ExamManageServiceImpl;
import com.henu.examsystem.serviceimpl.FillQuestionServiceImpl;
import com.henu.examsystem.serviceimpl.JudgeQuestionServiceImpl;
import com.henu.examsystem.serviceimpl.MultiQuestionServiceImpl;
import com.henu.examsystem.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PaperController {
    @Autowired
    FillQuestionServiceImpl fillQuestionService;
    @Autowired
    JudgeQuestionServiceImpl judgeQuestionService;
    @Autowired
    MultiQuestionServiceImpl multiQuestionService;
    @Autowired
    ExamManageServiceImpl examManageService;
    @GetMapping("/paperSearch")
    public ApiResult paperSearch(){
        List<ExamManage> examManages = examManageService.findAll();

        return ApiResultHandler.buildApiResult(200, "请求成功", examManages);
    }
    @GetMapping("/paperBegin/{id}")
    public ApiResult paperBegin(@PathVariable("id") Integer id,
                                HttpSession session
                                ){
        ExamManage examManage = examManageService.findById(id);
        session.setAttribute("examManage",examManage);
        return ApiResultHandler.buildApiResult(200, "请求成功", examManage);
    }

}
