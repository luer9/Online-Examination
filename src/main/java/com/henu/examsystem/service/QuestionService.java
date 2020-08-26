package com.henu.examsystem.service;

import com.henu.examsystem.entity.FillQuestion;
import com.henu.examsystem.entity.JudgeQuestion;
import com.henu.examsystem.entity.MultiQuestion;

import java.util.List;

/**
 * 项目名称：exam-system
 * 类 名 称：QuestionService
 * 类 描 述：TODO
 * 创建时间：2020/5/14 23:31
 * 创 建 人：10265
 */
public interface QuestionService {
    MultiQuestion getMulti(Integer qId);
    FillQuestion getFill(Integer qId);
    JudgeQuestion getJudge(Integer qId);
//    int addFill(FillQuestion questions);
//    int delFill(Integer qId);
//    int modifyFill(FillQuestion questions);
//    int addFill(FillQuestion questions);
//    int delFill(Integer qId);
//    int modifyFill(FillQuestion questions);
//    int addFill(FillQuestion questions);
//    int delFill(Integer qId);
//    int modifyFill(FillQuestion questions);
    List<FillQuestion> checkAllQuesBySubjectId(Integer sId);
//    List<FillQuestion> checkAllQuesBySIdAnd(Integer sId,Long qtId,int pageNum,int pageSize);

    //根据科目、题型、难易程度查题目
//	public List<Questions> checkAllQuesByQtAndqDL(Long sId,Long questionType,Double qDifficultyLevel);
//	public int countQuesByQtAndqDL(Long sId,Long questionType,Double qDifficultyLevel);
    //public List<Questions> checkAllQuesByQt(Long sId,Long questionType);

    //根据科目、题型、知识点、难易程度查题目
    List<FillQuestion> checkAllQuesByKpsAndQtAndDl(Long sId,List<Long> KnowPoints,Long questionType,Double qDiffiCultyLevel);
//    List<FillQuestion> checkAllQuesByKpsAndQtAndDl(Long sId,List<Long> KnowPoints,Long questionType,Double qDiffiCultyLevel,int pageNum,int pageSize);
    int countQuesByKpsAndQtAndDl(Integer sId,List<String> KnowPoints,Long questionType,Double qDiffiCultyLevel);

    //根据科目、题型（多个）、知识点查询题目
    List<FillQuestion> checkAllQuesByKpsAndQtAndDl(Long sId,List<String> konwPoints,List<Long> quesTypes,List<Double> qDiffiCultyLevels);

    //题目的抽取次数
    int countgetBy(Long qId);

    //根据试卷和题型和试卷类型取出试题，并根据难易度升序排序--和questions表外连接、
    List<FillQuestion> getQuestionsByEIdAndEQTId(Integer eId, Long eqtId, int eType);


}
