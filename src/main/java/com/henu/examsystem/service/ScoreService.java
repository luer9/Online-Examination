package com.henu.examsystem.service;

import com.henu.examsystem.entity.Score;

import java.util.List;

public interface ScoreService {
    public List<Score> findAll();

    public List<Score> findAllPage();

    public List<Score> findById(Integer studentId);

    public Score findByScoreId(Integer scoreId);

    public List<Score> findByExamCode(Integer examCode);

    public int deleteById(Integer scoreId);



    public int add(Score score);
}
