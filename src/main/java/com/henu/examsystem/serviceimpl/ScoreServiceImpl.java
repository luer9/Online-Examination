package com.henu.examsystem.serviceimpl;

import com.henu.examsystem.entity.Score;
import com.henu.examsystem.mapper.ScoreMapper;
import com.henu.examsystem.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired(required = false)
    private ScoreMapper scoreMapper;

    @Override
    public List<Score> findAllPage() {
        return scoreMapper.findAllPage();
    }

    @Override
    public int add(Score score) {
        return scoreMapper.add(score);
    }

    @Override
    public Score findByScoreId(Integer scoreId){return scoreMapper.findByScoreId(scoreId);}
    @Override
    public List<Score> findAll() {
        return scoreMapper.findAll();
    }

    @Override
    public List<Score> findById(Integer studentId) {
        return scoreMapper.findById(studentId);
    }

    @Override
    public int deleteById(Integer scoreId) {
        return scoreMapper.deleteById(scoreId);
    }

    @Override
    public List<Score> findByExamCode(Integer examCode) {
        return scoreMapper.findByExamCode(examCode);
    }
}
