package com.henu.examsystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henu.examsystem.entity.MultiQuestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//选择题
@Mapper
public interface MultiQuestionMapper {
    /**
     * select * from multiquestions where questionId in (
     * 	select questionId from papermanage where questionType = 1 and paperId = 1001
     * )
     */
    @Select("select * from MultiQuestion where questionId = #{questionId}")
    MultiQuestion findById(Integer questionId);
    @Select("select * from MultiQuestion where questionId in (select questionId from PaperManage where questionType = 1 and paperId = #{paperId})")
    List<MultiQuestion> findByIdAndType(Integer PaperId);

    @Select("select * from MultiQuestion")
    IPage<MultiQuestion> findAll(Page page);

    //yll
    @Select("select questionId,subject,question,section,score,level from  MultiQuestion")
    List<MultiQuestion> findAllPage();
    /**
     * 查询最后一条记录的questionId
     * @return MultiQuestion
     */
    @Select("select questionId from MultiQuestion order by questionId desc limit 1")
    MultiQuestion findOnlyQuestionId();

    @Options(useGeneratedKeys = true,keyProperty = "questionId")
    @Insert("insert into MultiQuestion(subject,question,answerA,answerB,answerC,answerD,rightAnswer,analysis,section,level) " +
            "values(#{subject},#{question},#{answerA},#{answerB},#{answerC},#{answerD},#{rightAnswer},#{analysis},#{section},#{level})")
    int add(MultiQuestion multiQuestion);

    @Select("select questionId from MultiQuestion  where subject =#{subject} order by rand() desc limit #{pageNo}")
    List<Integer> findBySubject(String subject, Integer pageNo);

    @Select("select multiQuestion.question,multiQuestion.score from multiQuestion,papermanage,exammanage "+
            "where #{id} = exammanage.examCode and exammanage.paperId=papermanage.paperId and papermanage.questionId = multiQuestion.questionId and papermanage.questionType = 3")
    List<MultiQuestion> findContent(Integer id);

}
