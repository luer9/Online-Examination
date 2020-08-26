package com.henu.examsystem.serviceimpl;

import com.henu.examsystem.entity.PaperManage;
import com.henu.examsystem.mapper.PaperManageMapper;
import com.henu.examsystem.service.PaperManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperManageServiceImpl implements PaperManageService {
    @Autowired(required = false)
    private PaperManageMapper paperManageMapper;
    @Override
    public List<PaperManage> findAllPage(){return paperManageMapper.findAllPage();}
    @Override
    public List<PaperManage> findById(Integer questionType,Integer paperId){return paperManageMapper.findById(questionType,paperId);}
    @Override
    public int isExist(Integer paperId,Integer questionType,Integer questionId){return paperManageMapper.isExist(paperId,questionType,questionId);}

    @Override
    public List<PaperManage> findByIdd(Integer paperId) {return paperManageMapper.findByIdd(paperId);}

    @Override
    public int add(Integer paperId,Integer questionType,Integer questionId) {return paperManageMapper.add(paperId,questionType,questionId);}
}
