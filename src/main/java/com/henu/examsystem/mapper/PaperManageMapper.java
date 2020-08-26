package com.henu.examsystem.mapper;

import com.henu.examsystem.entity.PaperManage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaperManageMapper {
    @Select("select * from papermanage")
    public List<PaperManage> findAllPage();
    @Select("select * from papermanage where paperId=#{paperId} and questionType = #{questionType}")
    public List<PaperManage> findById(Integer questionType,Integer paperId);
    @Select("select count(*) from papermanage where paperId=#{paperId} and questionType = #{questionType} and questionId=#{questionId}")
    public int isExist(Integer paperId, Integer questionType, Integer questionId);
    @Options(useGeneratedKeys = false,keyProperty = "paperId")
    @Insert("insert into papermanage(paperId,questionType,questionId) values(#{paperId},#{questionType},#{questionId})")
    public int add(Integer paperId,Integer questionType,Integer questionId);
    @Select("select * from papermanage where paperId=#{paperId}")
    List<PaperManage> findByIdd(Integer paperId);
}
