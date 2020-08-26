package com.henu.examsystem.serviceimpl;
/**
 * 项目名称：exam-system
 * 类 名 称：GeneratePaperServiceImpl
 * 类 描 述：TODO
 * 创建时间：2020/5/14 23:28
 * 创 建 人：10265
 */

import com.henu.examsystem.entity.FillQuestion;
import com.henu.examsystem.entity.JudgeQuestion;
import com.henu.examsystem.entity.MultiQuestion;
import com.henu.examsystem.entity.TempExam;
import com.henu.examsystem.service.FillQuestionService;
import com.henu.examsystem.service.GeneratePaperService;
import com.henu.examsystem.service.JudgeQuestionService;
import com.henu.examsystem.service.MultiQuestionService;
import com.henu.examsystem.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author yll
 * @description 自动组卷Impl
 * @date 2020/5/14
 */
public class GeneratePaperServiceImpl implements GeneratePaperService {


    @Autowired
    FillQuestionService fillQuestionService;
    @Autowired
    JudgeQuestionService judgeQuestionService;
    @Autowired
    MultiQuestionService multiQuestionService;

    //初始种群

    /**
     * count：种群数量
     * expectedExam：期望的试卷
     * List<FillQuestion>...  题库
     * kpCoverageRate：知识点所占权重
     * difficultyRate：难度系数所占权重
     */
    @Override
    public List<TempExam> cszq(int count, TempExam expectedExam, List<FillQuestion> fillQuestionsList, List<JudgeQuestion> judgeQuestionsList, List<MultiQuestion> multiQuestionsList) {
        //种群
        List<TempExam> unitList = new ArrayList<>();
        //各种题型信息
        List<Map<String, Object>> fillqtList = expectedExam.getFillqtList();
        List<Map<String, Object>> multiqtList = expectedExam.getMultiqtList();
        List<Map<String, Object>> judgeqtList = expectedExam.getJudgeqtList();
        //知识点
        Set<String> kpSet = expectedExam.getKnowPoints();
        TempExam unit;
        Random random = new Random();
//        Integer qtId;//题型id
//        Map<String, Object> fillqtMap, judgeqtMap, multiqtMap; //期望题型信息
        int fillqtNum, judgeqtNum, multiqtNum, index;

        for (int i = 0; i < count; i++) {
            System.out.println("第" + i + "次产生种群个体==================");
            unit = new TempExam(); //种群个体
            unit.setAdapterDegree(0.0);
            List<FillQuestion> fillQuestionList = unit.getFillQuestionsList();
            List<MultiQuestion> multiQuestionList = unit.getMultiQuestionsList();
            List<JudgeQuestion> judgeQuestionList = unit.getJudgeQuestionsList();

            //总分限制
            while (expectedExam.getTotalScore() != unit.getTotalScore()) {
                System.out.println("总分限制++++" + expectedExam.getTotalScore() + "+++++" + unit.getTotalScore());
                //清空试题信息
                unit.setFillQuestionsList(null);
                unit.setMultiQuestionsList(null);
                unit.setJudgeQuestionsList(null);


                // 各种题型
                // 题库中Fill该题型题目
                List<FillQuestion> FillTypeQuestions = getFillTypeQuestions(fillQuestionsList, kpSet);
                fillqtNum = FillTypeQuestions.size();

                for (int j = 0; j < fillqtNum; j++) {
                    // 从[0,oneTypeQuestions.size()-1)中任意取一个数
                    //左含又不含
                    //如果right<=0抛出异常
                    index = random.nextInt(FillTypeQuestions.size());

                    fillQuestionList.add(FillTypeQuestions.get(index));

                    // 将放入题库的题目排到末尾，避免选择重复题目
                    FillQuestion tempQues = FillTypeQuestions.get(fillqtNum - j - 1);
                    FillTypeQuestions.set(fillqtNum - j - 1, FillTypeQuestions.get(index));
                    FillTypeQuestions.set(index, tempQues);
                }


                // 题库中Multi该题型题目
                List<MultiQuestion> MultiTypeQuestions = getMultiTypeQuestions(multiQuestionsList, kpSet);
                multiqtNum = MultiTypeQuestions.size();

                for (int j = 0; j < multiqtNum; j++) {
                    // 从[0,oneTypeQuestions.size()-1)中任意取一个数
                    //左含又不含
                    //如果right<=0抛出异常
                    index = random.nextInt(MultiTypeQuestions.size());

                    multiQuestionList.add(MultiTypeQuestions.get(index));

                    // 将放入题库的题目排到末尾，避免选择重复题目
                    MultiQuestion tempQues = MultiTypeQuestions.get(fillqtNum - j - 1);
                    MultiTypeQuestions.set(fillqtNum - j - 1, MultiTypeQuestions.get(index));
                    MultiTypeQuestions.set(index, tempQues);
                }


                // 题库中Judge题型题目
                List<JudgeQuestion> JudgeTypeQuestions = getJudgeTypeQuestions(judgeQuestionsList, kpSet);

                judgeqtNum = JudgeTypeQuestions.size();

                for (int j = 0; j < judgeqtNum; j++) {
                    // 从[0,oneTypeQuestions.size()-1)中任意取一个数
                    //左含又不含
                    //如果right<=0抛出异常
                    index = random.nextInt(JudgeTypeQuestions.size());

                    judgeQuestionList.add(JudgeTypeQuestions.get(index));

                    // 将放入题库的题目排到末尾，避免选择重复题目
                    JudgeQuestion tempQues = JudgeTypeQuestions.get(fillqtNum - j - 1);
                    JudgeTypeQuestions.set(fillqtNum - j - 1, JudgeTypeQuestions.get(index));
                    JudgeTypeQuestions.set(index, tempQues);
                }

                //将题目放进unit中
                unit.setJudgeQuestionsList(judgeQuestionList);
                unit.setMultiQuestionsList(multiQuestionList);
                unit.setFillQuestionsList(fillQuestionList);
                //各种题型信息
                unit.setFillqtList(fillqtList);
                unit.setMultiqtList(multiqtList);
                unit.setJudgeqtList(judgeqtList);
                // 设置种群个体的得分
                unit.setTotalScore1();
                // 设置种群个体的难度系数
                unit.setDifficultyLevel1();
                // 设置种群个体知识点
                unit.setKnowPoints1();
            }
            unitList.add(unit);
        }

        // 设置知识点覆盖率以及适应度
        unitList = getKPCoverage(unitList, expectedExam);
        unitList = getAdaptionDegree(unitList, expectedExam, Constants.KP_COVERAGE_RATE, Constants.DIFFICULTY_RATE);
        return unitList;
    }


    //选择
    /*
     * 选择算子（轮盘赌选择） 适应度越强被选到的概率越大 unitList:初代种群 count:选择的次数（下一代种群的大小）
     * 返回值：List<TempExam> 进入下一代的种群
     *
     */
    @Override
    public List<TempExam> select(List<TempExam> unitList, int count) {
        System.out.println("选择");
        List<TempExam> selectedUnitList = new ArrayList<>();

        // 种群的适应度之和
        double allAdaptionDegree = 0;
        for (TempExam unit : unitList) {
            allAdaptionDegree += unit.getAdapterDegree();
        }

        Random random = new Random();
        int count1 = 0;
        // 次数限制
        while (selectedUnitList.size() != count) {
            System.out.println("选择种群的数量：" + selectedUnitList.size());
            count1 += 1;
            //选择次数超过10000次，直接退出
            if (count1 > 10000) {
                break;
            }

            double degree = 0.00;
            // 随机产生一个概率在0-allAdaptionDegree
            double randomDegree = random.nextInt(100) * 0.01 * allAdaptionDegree;

            for (int i = 0; i < unitList.size(); i++) {
                degree += unitList.get(i).getAdapterDegree();
                if (randomDegree <= degree) {

                    // 不重复选择
                    if (!selectedUnitList.contains(unitList.get(i))) {
                        selectedUnitList.add(unitList.get(i));
                    }
                    break;
                }
            }

        }

        return selectedUnitList;
    }


    //交叉
    /*
     * 交叉算子 一套试卷作为染色体，每一道题作为基因 按照题型分段，每种题型单点交叉，整套试卷就表现为分段多点交叉
     * 交叉点选择：在[0,N-2]（N为题目数量）产生随机数r，交换r位置的两个题目 交叉后很可能存在重复题目而冲突--重新选择题目（知识点相同，类型相同）
     *
     * unitList:初始种群 count:交叉后产生新的种群个体数量 expectedExam：期望试卷
     *
     */
    @Override
    public List<TempExam> cross(List<TempExam> unitList, int count, TempExam expectedExam) {
        System.out.println("交叉");
        List<TempExam> crossedUnitList = new ArrayList<>();
        Random random = new Random();
        List<FillQuestion> fillQuestionList = expectedExam.getFillQuestionsList();
        List<MultiQuestion> multiQuestionList = expectedExam.getMultiQuestionsList();
        List<JudgeQuestion> judgeQuestionList = expectedExam.getJudgeQuestionsList();
        int count1 = 0;
        //交叉后种群大小限制
        while (crossedUnitList.size() < count) {
            count1 += 1;
            //交叉次数过多，无法产生足够数量的交叉种群个数
            if (count1 > 10000) break;

            //随机选择一种题型：1,2,3
            int qtRand = (int) (Math.random() * 3) + 1;


            //随机选择两个染色体
            int indexOne = random.nextInt(unitList.size());
            int indexTwo = random.nextInt(unitList.size());
            TempExam unitOne, unitTwo;
            if (indexOne != indexTwo) {
                unitOne = unitList.get(indexOne);
                unitTwo = unitList.get(indexTwo);


                if (qtRand == 1) { //选择

                    //随机选择交叉位置——种群中数目数量都一样
                    int crossPostion = random.nextInt(unitOne.getMultiQuestionsList().size() - 1);
                    //保证交叉的分数和相同
                    //根据 题目 id 得到题目的分数
                    double scoreOne = getMultiScoreById(multiQuestionList, unitOne.getMultiQuestionsList().get(crossPostion).getQuestionId())
                            + getMultiScoreById(multiQuestionList, unitOne.getMultiQuestionsList().get(crossPostion + 1).getQuestionId());
                    double scoreTwo = getMultiScoreById(multiQuestionList, unitTwo.getMultiQuestionsList().get(crossPostion).getQuestionId())
                            + getMultiScoreById(multiQuestionList, unitTwo.getMultiQuestionsList().get(crossPostion + 1).getQuestionId());

                    if (scoreOne == scoreTwo) {
                        //产生两个新个体
                        TempExam unitNewOne = null;
                        TempExam unitNewTwo = null;
                        List<MultiQuestion> tempList1, tempList2;
                        //交叉位置后的两道题目
                        for (int i = crossPostion; i < crossPostion + 2; i++) {
                            tempList1 = unitOne.getMultiQuestionsList();
                            //确保题目没有重复
                            if (!tempList1.contains(unitTwo.getMultiQuestionsList().get(i))) {
                                //实列化此处
                                unitNewOne = new TempExam();
                                tempList1.set(i, unitTwo.getMultiQuestionsList().get(i));
                                unitNewOne.setMultiQuestionsList(tempList1);

                                //设置新个体的其他信息
                                unitNewOne.setFillQuestionsList(fillQuestionList);
                                unitNewOne.setJudgeQuestionsList(judgeQuestionList);
                                unitNewOne.setMultiQuestionsList(multiQuestionList);
                                unitNewOne.setTotalScore1();
                                unitNewOne.setDifficultyLevel1();
                                unitNewOne.setKnowPoints1();
                            }
                            tempList2 = unitTwo.getMultiQuestionsList();
                            if (!tempList2.contains(unitOne.getMultiQuestionsList().get(i))) {
                                unitNewTwo = new TempExam();
                                tempList2.set(i, unitOne.getMultiQuestionsList().get(i));
                                unitNewTwo.setMultiQuestionsList(tempList2);

                                //设置其他信息
                                unitNewTwo.setFillQuestionsList(fillQuestionList);
                                unitNewTwo.setJudgeQuestionsList(judgeQuestionList);
                                unitNewTwo.setMultiQuestionsList(multiQuestionList);
                                unitNewTwo.setTotalScore1();
                                unitNewTwo.setDifficultyLevel1();
                                unitNewTwo.setKnowPoints1();
                            }
                        }
                        if (crossedUnitList.size() < count) {
                            if (unitNewOne != null) {
                                crossedUnitList.add(unitNewOne);
                            }
                        }
                        if (crossedUnitList.size() < count) {
                            if (unitNewTwo != null) {
                                crossedUnitList.add(unitNewTwo);
                            }
                        }
                    }

                } else if (qtRand == 2) {  //填空

                    //随机选择交叉位置——种群中数目数量都一样
                    int crossPostion = random.nextInt(unitOne.getFillQuestionsList().size() - 1);
                    //保证交叉的分数和相同
                    //根据 题目 id 得到题目的分数
                    double scoreOne = getFillScoreById(fillQuestionList, unitOne.getFillQuestionsList().get(crossPostion).getQuestionId())
                            + getFillScoreById(fillQuestionList, unitOne.getFillQuestionsList().get(crossPostion + 1).getQuestionId());
                    double scoreTwo = getFillScoreById(fillQuestionList, unitTwo.getFillQuestionsList().get(crossPostion).getQuestionId())
                            + getFillScoreById(fillQuestionList, unitTwo.getFillQuestionsList().get(crossPostion + 1).getQuestionId());

                    if (scoreOne == scoreTwo) {
                        //产生两个新个体
                        TempExam unitNewOne = null;
                        TempExam unitNewTwo = null;
                        List<FillQuestion> tempList1, tempList2;
                        //交叉位置后的两道题目
                        for (int i = crossPostion; i < crossPostion + 2; i++) {
                            tempList1 = unitOne.getFillQuestionsList();
                            //确保题目没有重复
                            if (!tempList1.contains(unitTwo.getFillQuestionsList().get(i))) {
                                //实列化此处
                                unitNewOne = new TempExam();
                                tempList1.set(i, unitTwo.getFillQuestionsList().get(i));
                                unitNewOne.setFillQuestionsList(tempList1);

                                //设置新个体的其他信息
                                unitNewOne.setFillQuestionsList(fillQuestionList);
                                unitNewOne.setJudgeQuestionsList(judgeQuestionList);
                                unitNewOne.setMultiQuestionsList(multiQuestionList);
                                unitNewOne.setTotalScore1();
                                unitNewOne.setDifficultyLevel1();
                                unitNewOne.setKnowPoints1();
                            }
                            tempList2 = unitTwo.getFillQuestionsList();
                            if (!tempList2.contains(unitOne.getFillQuestionsList().get(i))) {
                                unitNewTwo = new TempExam();
                                tempList2.set(i, unitOne.getFillQuestionsList().get(i));
                                unitNewTwo.setFillQuestionsList(tempList2);

                                //设置其他信息
                                unitNewTwo.setFillQuestionsList(fillQuestionList);
                                unitNewTwo.setJudgeQuestionsList(judgeQuestionList);
                                unitNewTwo.setMultiQuestionsList(multiQuestionList);
                                unitNewTwo.setTotalScore1();
                                unitNewTwo.setDifficultyLevel1();
                                unitNewTwo.setKnowPoints1();
                            }
                        }
                        if (crossedUnitList.size() < count) {
                            if (unitNewOne != null) {
                                crossedUnitList.add(unitNewOne);
                            }
                        }
                        if (crossedUnitList.size() < count) {
                            if (unitNewTwo != null) {
                                crossedUnitList.add(unitNewTwo);
                            }
                        }
                    }

                } else if (qtRand == 3) {   //判断
                    //随机选择交叉位置——种群中数目数量都一样
                    int crossPostion = random.nextInt(unitOne.getJudgeQuestionsList().size() - 1);
                    //保证交叉的分数和相同
                    //根据 题目 id 得到题目的分数
                    double scoreOne = getJudgeScoreById(judgeQuestionList, unitOne.getJudgeQuestionsList().get(crossPostion).getQuestionId())
                            + getJudgeScoreById(judgeQuestionList, unitOne.getJudgeQuestionsList().get(crossPostion + 1).getQuestionId());
                    double scoreTwo = getJudgeScoreById(judgeQuestionList, unitTwo.getJudgeQuestionsList().get(crossPostion).getQuestionId())
                            + getJudgeScoreById(judgeQuestionList, unitTwo.getJudgeQuestionsList().get(crossPostion + 1).getQuestionId());

                    if (scoreOne == scoreTwo) {
                        //产生两个新个体
                        TempExam unitNewOne = null;
                        TempExam unitNewTwo = null;
                        List<JudgeQuestion> tempList1, tempList2;
                        //交叉位置后的两道题目
                        for (int i = crossPostion; i < crossPostion + 2; i++) {
                            tempList1 = unitOne.getJudgeQuestionsList();
                            //确保题目没有重复
                            if (!tempList1.contains(unitTwo.getJudgeQuestionsList().get(i))) {
                                //实列化此处
                                unitNewOne = new TempExam();
                                tempList1.set(i, unitTwo.getJudgeQuestionsList().get(i));
                                unitNewOne.setJudgeQuestionsList(tempList1);

                                //设置新个体的其他信息
                                unitNewOne.setFillQuestionsList(fillQuestionList);
                                unitNewOne.setJudgeQuestionsList(judgeQuestionList);
                                unitNewOne.setMultiQuestionsList(multiQuestionList);
                                unitNewOne.setTotalScore1();
                                unitNewOne.setDifficultyLevel1();
                                unitNewOne.setKnowPoints1();
                            }
                            tempList2 = unitTwo.getJudgeQuestionsList();
                            if (!tempList2.contains(unitOne.getJudgeQuestionsList().get(i))) {
                                unitNewTwo = new TempExam();
                                tempList2.set(i, unitOne.getJudgeQuestionsList().get(i));
                                unitNewTwo.setJudgeQuestionsList(tempList2);

                                //设置其他信息
                                unitNewTwo.setFillQuestionsList(fillQuestionList);
                                unitNewTwo.setJudgeQuestionsList(judgeQuestionList);
                                unitNewTwo.setMultiQuestionsList(multiQuestionList);
                                unitNewTwo.setTotalScore1();
                                unitNewTwo.setDifficultyLevel1();
                                unitNewTwo.setKnowPoints1();
                            }
                        }
                        if (crossedUnitList.size() < count) {
                            if (unitNewOne != null) {
                                crossedUnitList.add(unitNewOne);
                            }
                        }
                        if (crossedUnitList.size() < count) {
                            if (unitNewTwo != null) {
                                crossedUnitList.add(unitNewTwo);
                            }
                        }
                    }
                }
            }

        }
        // 计算知识点覆盖率以及适应度
        crossedUnitList = getKPCoverage(crossedUnitList, expectedExam);
        crossedUnitList = getAdaptionDegree(crossedUnitList, expectedExam, Constants.KP_COVERAGE_RATE, Constants.DIFFICULTY_RATE);

        return crossedUnitList;
    }

    //变异
    /*
     * 变异算子
     * unitList:初始种群
     * questionDB：题库
     * expectedExam:期望试卷
     *
     * 随机试卷中的一道题目进行变异（要求：题目题型相同、题号不同、知识点最好为Ue-(Ue ∩ Un)  Ue:期望试卷  Un：现有试卷
     * */
    @Override
    public List<TempExam> change(List<TempExam> unitList, List<FillQuestion> fillQuestionsList, List<JudgeQuestion> judgeQuestionsList, List<MultiQuestion> multiQuestionsList, TempExam expectedExam) {
        System.out.println("变异");
        Random random = new Random();
        int index, index1;
        FillQuestion tempFill;
        MultiQuestion tempMulti;
        JudgeQuestion tempJudge;
        Set<String> expectedKPSet = expectedExam.getKnowPoints(); //知识点集合
        Set<String> unitKPSet; //种群个体知识点
        Set<String> resultKPSet;
        List<FillQuestion> expectedFillList, tempFillList;
        List<MultiQuestion> expectedMultiList, tempMultiList;
        List<JudgeQuestion> expectedJudgeList, tempJudgeList;
        for (TempExam unit : unitList) {
            //随机变异得试题位置
            //随机选择一种题型：1,2,3
            int qtRand = (int) (Math.random() * 3) + 1;
            if (qtRand == 1) { //选择
                index = random.nextInt(unit.getMultiQuestionsList().size());
                tempMulti = new MultiQuestion();
                //变异得试题
                tempMulti = unit.getMultiQuestionsList().get(index);
                unitKPSet = unit.getKnowPoints();
                //期望新试题所在的知识点范围
                resultKPSet = getResultKPList(expectedKPSet, unitKPSet);

                //期望试题列表——如果resultKPSet为空，就不限制知识点
                expectedMultiList = getMultiTypeQuestions(multiQuestionsList, resultKPSet);

                //将新的试题替换掉原来的试题
                if(expectedMultiList.size() > 0) {
                    index1 = random.nextInt(expectedMultiList.size());
                    tempMultiList = unit.getMultiQuestionsList();
                    tempMultiList.set(index, expectedMultiList.get(index1));
                    unit.setMultiQuestionsList(tempMultiList);

                    //重新计算 试卷的几个信息
                    unit.setKnowPoints1();
                    unit.setTotalScore1();
                    unit.setDifficultyLevel1();
                    unit.setKnowPoints1();
                }else {
                    System.out.println(unit.geteId()+"种群个体没有可变异的待选试题");
                }

            } else if (qtRand == 2) { //填空
                index = random.nextInt(unit.getFillQuestionsList().size());
                tempFill = new FillQuestion();
                //变异得试题
                tempFill = unit.getFillQuestionsList().get(index);
                unitKPSet = unit.getKnowPoints();
                //期望新试题所在的知识点范围
                resultKPSet = getResultKPList(expectedKPSet, unitKPSet);

                //期望试题列表——如果resultKPSet为空，就不限制知识点
                expectedFillList = getFillTypeQuestions(fillQuestionsList, resultKPSet);

                //将新的试题替换掉原来的试题
                if(expectedFillList.size() > 0) {
                    index1 = random.nextInt(expectedFillList.size());
                    tempFillList = unit.getFillQuestionsList();
                    tempFillList.set(index, expectedFillList.get(index1));
                    unit.setFillQuestionsList(tempFillList);

                    //重新计算 试卷的几个信息
                    unit.setKnowPoints1();
                    unit.setTotalScore1();
                    unit.setDifficultyLevel1();
                    unit.setKnowPoints1();
                }else {
                    System.out.println(unit.geteId()+"种群个体没有可变异的待选试题");
                }

            } else if (qtRand == 3) { //判断
                index = random.nextInt(unit.getJudgeQuestionsList().size());
                tempJudge = new JudgeQuestion();
                //变异得试题
                tempJudge = unit.getJudgeQuestionsList().get(index);
                unitKPSet = unit.getKnowPoints();
                //期望新试题所在的知识点范围
                resultKPSet = getResultKPList(expectedKPSet, unitKPSet);

                //期望试题列表——如果resultKPSet为空，就不限制知识点
                expectedJudgeList = getJudgeTypeQuestions(judgeQuestionsList, resultKPSet);

                //将新的试题替换掉原来的试题
                if(expectedJudgeList.size() > 0) {
                    index1 = random.nextInt(expectedJudgeList.size());
                    tempJudgeList = unit.getJudgeQuestionsList();
                    tempJudgeList.set(index, expectedJudgeList.get(index1));
                    unit.setJudgeQuestionsList(tempJudgeList);

                    //重新计算 试卷的几个信息
                    unit.setKnowPoints1();
                    unit.setTotalScore1();
                    unit.setDifficultyLevel1();
                    unit.setKnowPoints1();
                }else {
                    System.out.println(unit.geteId()+"种群个体没有可变异的待选试题");
                }
            }
        }
        // 计算知识点覆盖率以及适应度
        unitList=getKPCoverage(unitList, expectedExam);
        unitList=getAdaptionDegree(unitList, expectedExam, Constants.KP_COVERAGE_RATE, Constants.DIFFICULTY_RATE);
        return unitList;
    }

    //结束
    @Override
    public boolean isEnd(List<TempExam> unitList, double expandAdapterDegree) {
        for (TempExam unit : unitList) {
            if (unit.getAdapterDegree() >= expandAdapterDegree) {
                return true;
            }
        }
        return false;
    }

    //Ue-(Ue ∩ Un)  U试e:期望卷  Un：现有试卷
    public Set<String> getResultKPList(Set<String> expectedKPSet, Set<String> unitKPSet) {
        List<String> intersectionKPList = new ArrayList<>();//交集
        Set<String> tempSet = new HashSet<>(expectedKPSet);
        Set<String> resultKPSet = new HashSet<>();

        //Ue ∩ Un
        for (String id : unitKPSet) {
            if (tempSet.add(id) == false) {
                intersectionKPList.add(id);
            }
        }

        //Ue-(Ue ∩ Un)
        for (String id : expectedKPSet) {
            if (!intersectionKPList.contains(id)) {
                resultKPSet.add(id);
            }
        }

        return resultKPSet;
    }



    // 计算知识点覆盖率
    public List<TempExam> getKPCoverage(List<TempExam> unitList, TempExam expectedExam) {
        int kpNum, expectedKpNum;
        for (TempExam unit : unitList) {
            kpNum = unit.getKnowPoints().size();
            expectedKpNum = expectedExam.getKnowPoints().size();
            unit.setKpCoverage(kpNum * 1.00 / expectedKpNum);
        }
        return unitList;
    }

    /*
     * 计算适应度 公式：f=1-(1-M/N)*f1-|Ep-P|*f2 M/N为知识点覆盖率 Ep：期望难度系数 P:实际难度系数
     *
     * unitList:种群 expectedExam：期望试卷 kpCoverage：知识点覆盖率所占权重 difficulty：难度系数所占权重
     */
    public List<TempExam> getAdaptionDegree(List<TempExam> unitList, TempExam expectedExam, double kpCoverage,
                                            double difficulty) {
        double adapterDegree;
        for (TempExam unit : unitList) {
            adapterDegree = 1 - (1 - unit.getKpCoverage()) * kpCoverage
                    - Math.abs(expectedExam.getDifficultyLevel() - unit.getDifficultyLevel()) * difficulty;
            unit.setAdapterDegree(adapterDegree);
        }
        return unitList;
    }


    // 从题库中选出Judge题型的题目--以及知识点符合
    public List<JudgeQuestion> getJudgeTypeQuestions(List<JudgeQuestion> questionsDB, Set<String> kpSet) {
        List<JudgeQuestion> oneTypeQuestions = new ArrayList<>();
        for (JudgeQuestion questions : questionsDB) {
            // 知识点符合
            if (kpSet != null) {
                if (kpSet.contains(questions.getSection())) {
                    oneTypeQuestions.add(questions);
                }
					/*if(kpSet.add(questions.getKonwledgePointId())==false) {
						oneTypeQuestions.add(questions);
					}*/

            } else {
                oneTypeQuestions.add(questions);
            }
        }

        return oneTypeQuestions;
    }

    // 从题库中选出Multi题型的题目--以及知识点符合
    public List<MultiQuestion> getMultiTypeQuestions(List<MultiQuestion> questionsDB, Set<String> kpSet) {
        List<MultiQuestion> oneTypeQuestions = new ArrayList<>();
        for (MultiQuestion questions : questionsDB) {
            // 知识点符合
            if (kpSet != null) {
                if (kpSet.contains(questions.getSection())) {
                    oneTypeQuestions.add(questions);
                }
					/*if(kpSet.add(questions.getKonwledgePointId())==false) {
						oneTypeQuestions.add(questions);
					}*/

            } else {
                oneTypeQuestions.add(questions);
            }
        }

        return oneTypeQuestions;
    }

    // 从题库中选出Fill题型的题目--以及知识点符合
    public List<FillQuestion> getFillTypeQuestions(List<FillQuestion> questionsDB, Set<String> kpSet) {
        List<FillQuestion> oneTypeQuestions = new ArrayList<>();
        for (FillQuestion questions : questionsDB) {
            // 知识点符合
            if (kpSet != null) {
                if (kpSet.contains(questions.getSection())) {
                    oneTypeQuestions.add(questions);
                }
					/*if(kpSet.add(questions.getKonwledgePointId())==false) {
						oneTypeQuestions.add(questions);
					}*/

            } else {
                oneTypeQuestions.add(questions);
            }
        }

        return oneTypeQuestions;
    }


    // 根据qtid得到选择题目的分数
    public double getMultiScoreById(List<MultiQuestion> multipt, Integer qtId) {
        for (MultiQuestion multiQuestion : multipt) {
            if (multiQuestion.getQuestionId().toString().equals(qtId.toString())) {
                //不能是int类型，应该是double
                //return Integer.parseInt(map.get("qtScore").toString());
                return Double.valueOf(multiQuestion.getScore());
            }
        }
        return 0;
    }

    // 根据qtid得到选择题目的分数
    public double getFillScoreById(List<FillQuestion> fillpt, Integer qtId) {
        for (FillQuestion fillQuestion : fillpt) {
            if (fillQuestion.getQuestionId().toString().equals(qtId.toString())) {
                //不能是int类型，应该是double
                //return Integer.parseInt(map.get("qtScore").toString());
                return Double.valueOf(fillQuestion.getScore());
            }
        }
        return 0;
    }

    // 根据qtid得到选择题目的分数
    public double getJudgeScoreById(List<JudgeQuestion> judgept, Integer qtId) {
        for (JudgeQuestion judgeQuestion : judgept) {
            if (judgeQuestion.getQuestionId().toString().equals(qtId.toString())) {
                //不能是int类型，应该是double
                //return Integer.parseInt(map.get("qtScore").toString());
                return Double.valueOf(judgeQuestion.getScore());
            }
        }
        return 0;
    }
}