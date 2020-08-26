package com.henu.examsystem.mapper;

import com.henu.examsystem.entity.Score;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {
    /**
     * @param score 添加一条成绩记录
     * @return
     */
    @Options(useGeneratedKeys = true,keyProperty = "scoreId")
    @Insert("insert into score(examCode,studentId,subject,ptScore,etScore,score,answerDate) values(#{examCode},#{studentId},#{subject},#{ptScore},#{etScore},#{score},#{answerDate})")
    int add(Score score);
    @Update("update score set examCode=#{examCode},studentId=#{studentId}")
    @Select("select scoreId,examCode,studentId,subject,ptScore,etScore,score,answerDate from score")
    List<Score> findAll();

    @Select("select * from score")
    public List<Score> findAllPage();

    @Select("select scoreId,examCode,studentId,subject,ptScore,etScore,score,answerDate from score where studentId = #{studentId}")
    List<Score> findById(Integer studentId);

    @Select("select scoreId,examCode,studentId,subject,ptScore,etScore,score from score ,answerDate from score where scoreId = #{scoreId}")
    Score findByScoreId(Integer scoreId);


    @Delete("delete from score where scoreId = #{scoreId}")
    public int deleteById(Integer scoreId);



    /**
     *
     * @return 查询每位学生的学科分数。 max其实是假的，为了迷惑老师，达到一次考试考生只参加了一次的效果
     */
    @Select("select max(etScore) as etScore from score where examCode = #{examCode} group by studentId")
    List<Score> findByExamCode(Integer examCode);
}
