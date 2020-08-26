package com.henu.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henu.examsystem.entity.FillQuestion;

import java.util.List;

public interface FillQuestionService {

    FillQuestion findById(Integer questionId);

    List<FillQuestion> findByIdAndType(Integer paperId);

    IPage<FillQuestion> findAll(Page<FillQuestion> page);

    FillQuestion findOnlyQuestionId();

    int add(FillQuestion fillQuestion);

    List<Integer> findBySubject(String subject, Integer pageNo);
    //yll
    List<FillQuestion> findAllPage();
    List<FillQuestion> findContent(Integer id);
}
