package com.henu.examsystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henu.examsystem.entity.JudgeQuestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//判断题

@Mapper
public interface JudgeQuestionMapper {

    @Select("select * from JudgeQuestion where questionId in (select questionId from PaperManage where questionType = 3 and paperId = #{paperId})")
    List<JudgeQuestion> findByIdAndType(Integer paperId);

    @Select("select * from JudgeQuestion")
    IPage<JudgeQuestion> findAll(Page page);

    /**
     * 查询最后一条记录的questionId
     * @return JudgeQuestion
     */
    @Select("select questionId from JudgeQuestion order by questionId desc limit 1")
    JudgeQuestion findOnlyQuestionId();

    @Insert("insert into JudgeQuestion(subject,question,answer,analysis,level,section) values " +
            "(#{subject},#{question},#{answer},#{analysis},#{level},#{section})")
    int add(JudgeQuestion judgeQuestion);

    @Select("select questionId from JudgeQuestion  where subject=#{subject}  order by rand() desc limit #{pageNo}")
    List<Integer> findBySubject(String subject, Integer pageNo);
    @Select("select * from JudgeQuestion where questionId= #{questionId}")
    JudgeQuestion findById(Integer questionId);

    //yll
    @Select("select subject,question,section,score,level from judgeQuestion")
    public List<JudgeQuestion> findAllPage();
    //yll
    @Select("select judgeQuestion.question,judgeQuestion.score from judgeQuestion,papermanage,exammanage "+
            "where #{id} = exammanage.examCode and exammanage.paperId=papermanage.paperId and papermanage.questionId = judgeQuestion.questionId and papermanage.questionType = 2")
    public List<JudgeQuestion> findContent(Integer id);
}
