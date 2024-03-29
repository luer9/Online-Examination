package com.henu.examsystem.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henu.examsystem.entity.FillQuestion;
import com.henu.examsystem.mapper.FillQuestionMapper;
import com.henu.examsystem.service.FillQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FillQuestionServiceImpl implements FillQuestionService {

    @Autowired(required = false)
    private FillQuestionMapper fillQuestionMapper;

    @Override
    public FillQuestion findById(Integer questionId) {return fillQuestionMapper.findById(questionId);}
    //    @Override
//    public MultiQuestion findById(Integer questionId){ return fillQuestionMapper.findById(questionId);}

    @Override
    public List<FillQuestion> findByIdAndType(Integer paperId) {
        return fillQuestionMapper.findByIdAndType(paperId);
    }

    @Override
    public IPage<FillQuestion> findAll(Page<FillQuestion> page) {
        return fillQuestionMapper.findAll(page);
    }

    @Override
    public FillQuestion findOnlyQuestionId() {
        return fillQuestionMapper.findOnlyQuestionId();
    }

    @Override
    public int add(FillQuestion fillQuestion) {
        return fillQuestionMapper.add(fillQuestion);
    }
    @Override
    public List<Integer> findBySubject(String subject, Integer pageNo) {
        return fillQuestionMapper.findBySubject(subject,pageNo);
    }
    /**
     * yll
     * */
    @Override
    public List<FillQuestion> findAllPage() {
        return fillQuestionMapper.findAllPage();
    }
    @Override
    public List<FillQuestion> findContent(Integer id) {
        return fillQuestionMapper.findContent(id);
    }

}
