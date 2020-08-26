package com.henu.examsystem.entity;/**
 * 项目名称：exam-system
 * 类 名 称：TempExam
 * 类 描 述：TODO
 * 创建时间：2020/5/14 22:53
 * 创 建 人：10265
 */

/**
 * @author yll
 * @description 临时试卷
 * @date 2020/5/14
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author piperChan
 *临时的试卷表
 */
public class TempExam {
    private Long eId;
    private int totalScore;
    //试卷的难度系数
    private double difficultyLevel;
    //试卷中所有的知识点----知识点用set存储--
    private Set<String> knowPoints;
    //各种题型的题目数量
    private List<Map<String, Object>> fillqtList;
    private List<Map<String, Object>> multiqtList;
    private List<Map<String, Object>> judgeqtList;
    //题目
    private List<FillQuestion> fillQuestionsList; //填空题
    private List<JudgeQuestion> judgeQuestionsList;//判断题
    private List<MultiQuestion> multiQuestionsList; //选择题
    //适应度
    private double adapterDegree;
    //知识点覆盖率
    private double kpCoverage;

    public TempExam() {
        this.fillQuestionsList = new ArrayList<FillQuestion>();
        this.judgeQuestionsList = new ArrayList<JudgeQuestion>();
        this.multiQuestionsList = new ArrayList<MultiQuestion>();
        this.adapterDegree=0.0;
        this.kpCoverage=0.0;
    }

    public TempExam(Long eId, int totalScore, double difficultyLevel, Set<String> knowPoints,
                    List<Map<String, Object>> fillqtList,
                    List<Map<String, Object>> multiqtList,
                    List<Map<String, Object>> judgeqtList) {
        this.eId = eId;
        this.totalScore = totalScore;
        this.difficultyLevel = difficultyLevel;
        this.knowPoints = knowPoints;
        this.fillqtList = fillqtList;
        this.judgeqtList = judgeqtList;
        this.multiqtList = multiqtList;
        this.fillQuestionsList = new ArrayList<FillQuestion>();
        this.judgeQuestionsList = new ArrayList<JudgeQuestion>();
        this.multiQuestionsList = new ArrayList<MultiQuestion>();
        this.adapterDegree=0.0;
        this.kpCoverage=0.0;
    }


    public TempExam(Long eId, int totalScore, double difficultyLevel, Set<String> knowPoints,
                    List<Map<String, Object>> qtList, List<FillQuestion> fillQuestionsList,
                    List<JudgeQuestion> judgeQuestionsList,
                    List<MultiQuestion> multiQuestionsList, double adapterDegree, double kpCoverage) {
        this.eId = eId;
        this.totalScore = totalScore;
        this.difficultyLevel = difficultyLevel;
        this.knowPoints = knowPoints;
        this.fillqtList = fillqtList;
        this.judgeqtList = judgeqtList;
        this.multiqtList = multiqtList;
        this.fillQuestionsList = new ArrayList<FillQuestion>();
        this.judgeQuestionsList = new ArrayList<JudgeQuestion>();
        this.multiQuestionsList = new ArrayList<MultiQuestion>();
        this.adapterDegree = adapterDegree;
        this.kpCoverage = kpCoverage;
    }


    public Long geteId() {
        return eId;
    }
    public void seteId(Long eId) {
        this.eId = eId;
    }
    public int getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    //设置现有试卷总分
    public void setTotalScore1() {
        int qtId;
        int sum=0;
        int score = 0;
        for(FillQuestion questions:fillQuestionsList) {
            qtId = questions.getQuestionId();
            score = questions.getScore();
//            for(Map<String, Object> map:qtList) {
//                if(qtId.toString().equals(map.get("qtId").toString())) {
                    sum += score;
//                }
//            }
        }
        for(JudgeQuestion questions:judgeQuestionsList) {
            qtId = questions.getQuestionId();
            score = questions.getScore();
            /*for(Map<String, Object> map:qtList) {
                if(qtId.toString().equals(map.get("qtId").toString())) {*/
                sum += score;
//                }
//            }
        }
        for(MultiQuestion questions:multiQuestionsList) {
            qtId = questions.getQuestionId();
            score = questions.getScore();
//            for(Map<String, Object> map:qtList) {
//                if(qtId.toString().equals(map.get("qtId").toString())) {
                    sum += score;
//                }
//            }
        }
        this.totalScore=sum;
    }

    public double getDifficultyLevel() {
        return difficultyLevel;
    }
    public void setDifficultyLevel(double difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    //设置试卷的难度系数(所有题目分数*难度系数/总分)
    public void setDifficultyLevel1() {
        double difficult=0.0;
        int qtId, score;

        for(FillQuestion questions:fillQuestionsList) {
            qtId = questions.getQuestionId();
            score = questions.getScore();
            difficult += score * Double.parseDouble(questions.getLevel()); //分数*难度系数
        }
        for(JudgeQuestion questions:judgeQuestionsList) {
            qtId = questions.getQuestionId();
            score = questions.getScore();
            difficult += score * Double.parseDouble(questions.getLevel()); //分数*难度系数
        }
        for(MultiQuestion questions:multiQuestionsList) {
            qtId = questions.getQuestionId();
            score = questions.getScore();
            difficult += score * Double.parseDouble(questions.getLevel()); //分数*难度系数

        }

        this.difficultyLevel = difficult/totalScore;
    }

    public Set<String> getKnowPoints() {
        return knowPoints;
    }

    public void setKnowPoints(Set<String> knowPoints) {
        this.knowPoints = knowPoints;
    }

    //设置试卷的知识点
    public void setKnowPoints1() {
        Set<String> knowPoints=new HashSet<>();
        for(FillQuestion questions:fillQuestionsList) {
            knowPoints.add(questions.getSection());
        }
        for(JudgeQuestion questions:judgeQuestionsList) {
            knowPoints.add(questions.getSection());
        }
        for(MultiQuestion questions:multiQuestionsList) {
            knowPoints.add(questions.getSection());
        }

        this.knowPoints=knowPoints;
    }

    public List<Map<String, Object>> getFillqtList() {
        return fillqtList;
    }

    public void setFillqtList(List<Map<String, Object>> fillqtList) {
        this.fillqtList = fillqtList;
    }

    public List<Map<String, Object>> getMultiqtList() {
        return multiqtList;
    }

    public void setMultiqtList(List<Map<String, Object>> multiqtList) {
        this.multiqtList = multiqtList;
    }

    public List<Map<String, Object>> getJudgeqtList() {
        return judgeqtList;
    }

    public void setJudgeqtList(List<Map<String, Object>> judgeqtList) {
        this.judgeqtList = judgeqtList;
    }

    public double getAdapterDegree() {
        return adapterDegree;
    }

    public double getKpCoverage() {
        return kpCoverage;
    }


    public void setAdapterDegree(double adapterDegree) {
        this.adapterDegree = adapterDegree;
    }

    public void setKpCoverage(double kpCoverage) {
        this.kpCoverage = kpCoverage;
    }

    public List<FillQuestion> getFillQuestionsList() {
        return fillQuestionsList;
    }

    public void setFillQuestionsList(List<FillQuestion> fillQuestionsList) {
        this.fillQuestionsList = fillQuestionsList;
    }

    public List<JudgeQuestion> getJudgeQuestionsList() {
        return judgeQuestionsList;
    }

    public void setJudgeQuestionsList(List<JudgeQuestion> judgeQuestionsList) {
        this.judgeQuestionsList = judgeQuestionsList;
    }

    public List<MultiQuestion> getMultiQuestionsList() {
        return multiQuestionsList;
    }

    public void setMultiQuestionsList(List<MultiQuestion> multiQuestionsList) {
        this.multiQuestionsList = multiQuestionsList;
    }
}

