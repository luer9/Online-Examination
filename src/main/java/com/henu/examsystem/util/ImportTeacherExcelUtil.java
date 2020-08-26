package com.henu.examsystem.util;

import com.henu.examsystem.entity.Teacher;
import com.henu.examsystem.serviceimpl.TeacherServiceImpl;
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
@Service
public class ImportTeacherExcelUtil {

    @Autowired
    private TeacherServiceImpl  teacherService;


    public boolean batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
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
        Teacher teacher;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            teacher = new Teacher();
            for (int col = 0;col <= 8; col++)
            {
                row.getCell(col).setCellType(Cell.CELL_TYPE_STRING);
            }
            String name = row.getCell(0).getStringCellValue();
            String institute = row.getCell(1).getStringCellValue();
            String sex = row.getCell(2).getStringCellValue();
            String tel = row.getCell(3).getStringCellValue();
            String email = row.getCell(4).getStringCellValue();
            String pwd = row.getCell(5).getStringCellValue();
            String cardId = row.getCell(6).getStringCellValue();
            String type = row.getCell(7).getStringCellValue();
            String role = row.getCell(8).getStringCellValue();
            teacher.setTeacherName(name);
            teacher.setInstitute(institute);
            teacher.setSex(sex);
            teacher.setTel(tel);
            teacher.setEmail(email);
            teacher.setPwd(pwd);
            teacher.setCardId(cardId);
            teacher.setType(type);
            teacher.setRole(role);
            System.out.println(teacher);
            int i=0;
            try {
                i = teacherService.findByName(teacher.getTeacherName());
            }
            catch (Exception e){
                System.out.println("无对应的对象所以选择插入");
            }
            if (i == 0) {
                try {
                    teacherService.add(teacher);
                    System.out.println(" 插入 " + teacher);
                }catch (Exception e){
                    System.out.println("出错!");
                }

            } else {
                teacherService.update(teacher);
                System.out.println(" 更新 " + teacher);
            }
        }

        return notNull;
    }
}
