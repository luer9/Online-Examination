package com.henu.examsystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henu.examsystem.entity.FillQuestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//填空题
@Mapper
public interface FillQuestionMapper {

    @Select("select * from FillQuestion where questionId in (select questionId from PaperManage where questionType = 2 and paperId = #{paperId})")
    List<FillQuestion> findByIdAndType(Integer paperId);

    @Select("select * from FillQuestion")
    IPage<FillQuestion> findAll(Page page);

    /**
     * 查询最后一条questionId
     * @return FillQuestion
     */
    @Select("select questionId from FillQuestion order by questionId desc limit 1")
    FillQuestion findOnlyQuestionId();

    @Options(useGeneratedKeys = true,keyProperty ="questionId" )
    @Insert("insert into FillQuestion(subject,question,answer,analysis,level,section) values " +
            "(#{subject,},#{question},#{answer},#{analysis},#{level},#{section})")
    int add(FillQuestion fillQuestion);

    @Select("select questionId from FillQuestion where subject = #{subject} order by rand() desc limit #{pageNo}")
    List<Integer> findBySubject(String subject, Integer pageNo);
    @Select("select * from FillQuestion where questionId = #{questionId}")
    FillQuestion findById(Integer questionId);

    //yll
    @Select("select subject,question,section,score,level from fillQuestion")
    public List<FillQuestion> findAllPage();

    @Select("select fillQuestion.question,fillQuestion.score from fillQuestion,papermanage,exammanage "+
            "where #{id} = exammanage.examCode and exammanage.paperId=papermanage.paperId and papermanage.questionId = fillQuestion.questionId and papermanage.questionType = 1")
    public List<FillQuestion> findContent(Integer id);
}
