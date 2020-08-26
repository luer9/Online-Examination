package com.henu.examsystem.service;

import com.henu.examsystem.entity.FillQuestion;
import com.henu.examsystem.entity.JudgeQuestion;
import com.henu.examsystem.entity.MultiQuestion;
import com.henu.examsystem.entity.TempExam;

import java.util.List;

/**
 * 项目名称：exam-system
 * 类 名 称：GeneratePaperService
 * 类 描 述：TODO
 * 创建时间：2020/5/14 22:23
 * 创 建 人：luer
 */
public interface GeneratePaperService {
    //初始种群
    List<TempExam> cszq(int count, TempExam expectedExam,
                        List<FillQuestion> fillQuestionsList,
                        List<JudgeQuestion> judgeQuestionsList,
                        List<MultiQuestion> multiQuestionsList);
    //选择算子
    List<TempExam> select(List<TempExam> unitList,int count);
    //交叉算子
    List<TempExam> cross(List<TempExam> unitList,int count,TempExam expectedExam);
    //变异算子
    List<TempExam> change(List<TempExam> unitList, List<FillQuestion> fillQuestionsList,
                          List<JudgeQuestion> judgeQuestionsList,
                          List<MultiQuestion> multiQuestionsList,
                          TempExam expectedExam);
    //判断结束
    boolean isEnd(List<TempExam> unitList,double expandAdapterDegree);
}
