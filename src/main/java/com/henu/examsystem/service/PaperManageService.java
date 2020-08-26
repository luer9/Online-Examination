package com.henu.examsystem.service;


import com.henu.examsystem.entity.PaperManage;

import java.util.List;

public interface PaperManageService {
    public List<PaperManage> findAllPage();
    public List<PaperManage> findById(Integer questionType, Integer paperId);
    int isExist(Integer paperId, Integer questionType, Integer questionId);
    public int add(Integer paperId, Integer questionType, Integer questionId);
    public List<PaperManage> findByIdd(Integer paperId);
}
