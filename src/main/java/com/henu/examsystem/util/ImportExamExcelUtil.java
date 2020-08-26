package com.henu.examsystem.util;

import com.henu.examsystem.entity.FillQuestion;
import com.henu.examsystem.entity.JudgeQuestion;
import com.henu.examsystem.entity.MultiQuestion;
import com.henu.examsystem.serviceimpl.FillQuestionServiceImpl;
import com.henu.examsystem.serviceimpl.JudgeQuestionServiceImpl;
import com.henu.examsystem.serviceimpl.MultiQuestionServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportExamExcelUtil {

    @Autowired
    private FillQuestionServiceImpl fillQuesService;  // 1
    @Autowired
    private JudgeQuestionServiceImpl judgeQuesService; // 2
    @Autowired
    private MultiQuestionServiceImpl multiQuesService; // 3


    public boolean batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
//        List<Student> userList = new ArrayList<Student>();
        List<FillQuestion> fillListQuestion = new ArrayList<FillQuestion>();
        List<JudgeQuestion> judgeListQuestion = new ArrayList<JudgeQuestion>();
        List<MultiQuestion> multiListQuestion = new ArrayList<MultiQuestion>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        System.out.println(sheet);
        if (sheet != null) {
            notNull = true;
        }
//        Student student;
        FillQuestion fillQuestion;
        JudgeQuestion judgeQuestion;
        MultiQuestion multiQuestion;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            fillQuestion = new FillQuestion();
            judgeQuestion = new JudgeQuestion();
            multiQuestion = new MultiQuestion();
//            student = new Student();/
//            for (int col = 0; col <= 8; col++) {
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
//            }
            String type = row.getCell(0).getStringCellValue();
            System.out.println("----->"+type);
            if(type.equals("1")||type.equals("2")){
                for (int col = 1; col <= 7; col++) {
                    row.getCell(col).setCellType(Cell.CELL_TYPE_STRING);
                    System.out.println("1debug input-----");
                }
            }else if(type.equals("3")){
                for (int col = 1; col <= 11; col++) {
                    row.getCell(col).setCellType(Cell.CELL_TYPE_STRING);
                    System.out.println("2debug input-----");
                }
            }
            if (type.equals("1")) {

                String subject = row.getCell(1).getStringCellValue();
                String question = row.getCell(2).getStringCellValue();
                String answer = row.getCell(3).getStringCellValue();
                String analysis = row.getCell(4).getStringCellValue();
                Integer score = Integer.valueOf(row.getCell(5).getStringCellValue());
                System.out.println("score::::"+score);
                String level = row.getCell(6).getStringCellValue();
                String section = row.getCell(7).getStringCellValue();
                fillQuestion.setSubject(subject);
                fillQuestion.setQuestion(question);
                fillQuestion.setAnswer(answer);
                fillQuestion.setScore(score);
                fillQuestion.setLevel(level);
                fillQuestion.setAnalysis(analysis);
                fillQuestion.setSection(section);
                try {
                    fillQuesService.add(fillQuestion); //没有处理异常
                }
                catch (Exception e){
                    System.out.println("6");
                }
                System.out.println(" 插入 " + fillQuestion);

            } else if (type.equals("2")) {

                String subject = row.getCell(1).getStringCellValue();
                String question = row.getCell(2).getStringCellValue();
                String answer = row.getCell(3).getStringCellValue();
                String analysis = row.getCell(4).getStringCellValue();
                Integer score = Integer.valueOf(row.getCell(5).getStringCellValue());
                System.out.println("score::::"+score);
                String level = row.getCell(6).getStringCellValue();
                String section = row.getCell(7).getStringCellValue();
                judgeQuestion.setSubject(subject);
                judgeQuestion.setQuestion(question);
                judgeQuestion.setAnswer(answer);
                judgeQuestion.setScore(score);
                judgeQuestion.setLevel(level);
                judgeQuestion.setAnalysis(analysis);
                judgeQuestion.setSection(section);
                try{
                    judgeQuesService.add(judgeQuestion); //没有处理异常
                }catch (Exception e){
                    System.out.println("6");
                }

                System.out.println(" 插入 " + judgeQuestion);

            } else if (type.equals("3")) {

                String subject = row.getCell(1).getStringCellValue();
                String question = row.getCell(2).getStringCellValue();
                String answerA = row.getCell(3).getStringCellValue();
                String answerB = row.getCell(4).getStringCellValue();
                String answerC = row.getCell(5).getStringCellValue();
                String answerD = row.getCell(6).getStringCellValue();
                String rightAnswer = row.getCell(7).getStringCellValue();
                String analysis =  row.getCell(8).getStringCellValue();
                Integer score = Integer.valueOf(row.getCell(9).getStringCellValue());
                String section = row.getCell(10).getStringCellValue();
                String level = row.getCell(11).getStringCellValue();
                multiQuestion.setSubject(subject);
                multiQuestion.setQuestion(question);
                multiQuestion.setAnalysis(analysis);
                multiQuestion.setAnswerA(answerA);
                multiQuestion.setAnswerB(answerB);
                multiQuestion.setAnswerC(answerC);
                multiQuestion.setAnswerD(answerD);
                multiQuestion.setRightAnswer(rightAnswer);
                multiQuestion.setScore(score);
                multiQuestion.setSection(section);
                multiQuestion.setLevel(level);

                try{
                    multiQuesService.add(multiQuestion); //没有处理异常
                }catch (Exception e){
                    System.out.println("6");
                }

                System.out.println(" 插入 " + multiQuestion);
            } else {
                System.out.println("无效数据");
            }
        }

        return notNull;
    }
}
