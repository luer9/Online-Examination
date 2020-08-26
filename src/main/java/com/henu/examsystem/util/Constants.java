package com.henu.examsystem.util;/**
 * 项目名称：exam-system
 * 类 名 称：Constants
 * 类 描 述：TODO
 * 创建时间：2020/5/15 0:21
 * 创 建 人：10265
 */

/**
 * @author yll
 * @description 常数
 * @date 2020/5/15
 */
public class Constants {


    //difficulty_level难度系数取值
    public static Double DIFFICULTY_LEVEL_VERYEASY=(double) 0.1;
    public static Double DIFFICULTY_LEVEL_EASY=(double) 0.3;
    public static Double DIFFICULTY_LEVEL_MEDIUM=(double) 0.5;
    public static Double DIFFICULTY_LEVEL_HARD=(double) 0.7;
    public static Double DIFFICULTY_LEVEL_VERYHARD=(double) 0.9;


    public static double KP_COVERAGE_RATE = 0;//知识点覆盖率 占 适应度 比率
    public static double DIFFICULTY_RATE = 1;//难度系数  占 适应度比率

    //期望适应度
    public static double EXPAND_ADATPER=0.95;
    //最大迭代次数
    public static int RUN_Count=500;
}
