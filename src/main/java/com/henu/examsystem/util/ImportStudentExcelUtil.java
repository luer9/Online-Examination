package com.henu.examsystem.util;

import com.henu.examsystem.entity.Student;
import com.henu.examsystem.serviceimpl.StudentServiceImpl;
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
public class ImportStudentExcelUtil {

    @Autowired
    private StudentServiceImpl studentService;


    public boolean batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        List<Student> userList = new ArrayList<Student>();
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
        if (sheet != null) {
            notNull = true;
        }
        Student student;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            student = new Student();
            for (int col = 0;col <= 10; col++)
            {
                row.getCell(col).setCellType(Cell.CELL_TYPE_STRING);
            }
            String name = row.getCell(0).getStringCellValue();
            String grade = row.getCell(1).getStringCellValue();
            String major = row.getCell(2).getStringCellValue();
            String clazz = row.getCell(3).getStringCellValue();
            String institute = row.getCell(4).getStringCellValue();
            String tel = row.getCell(5).getStringCellValue();
            String email = row.getCell(6).getStringCellValue();
            String pwd = row.getCell(7).getStringCellValue();
            String cardId = row.getCell(8).getStringCellValue();
            String sex = row.getCell(9).getStringCellValue();
            String role = row.getCell(10).getStringCellValue();
            student.setStudentName(name);
            student.setGrade(grade);
            student.setMajor(major);
            student.setClazz(clazz);
            student.setInstitute(institute);
            student.setTel(tel);
            student.setEmail(email);
            student.setPwd(pwd);
            student.setCardId(cardId);
            student.setSex(sex);
            student.setRole(role);
            System.out.println(student);
            int i=0;
            try {
                i = studentService.findByName(student.getStudentName());
            }
            catch (Exception e){
                System.out.println("无对应的对象所以选择插入");
            }
            if (i == 0) {
                try {
                    studentService.add(student);
                    System.out.println(" 插入 " + student);
                }catch (Exception e){
                    System.out.println("出错!");
                }

            } else {
                studentService.update(student);
                System.out.println(" 更新 " + student);
            }
        }

        return notNull;
    }
}
