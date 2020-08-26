package com.henu.examsystem.controller;/**
 * 项目名称：exam-system
 * 类 名 称：ExportExcelControl
 * 类 描 述：TODO
 * 创建时间：2020/5/23 13:13
 * 创 建 人：10265
 */

import com.henu.examsystem.util.DownloadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author yll
 * @description 导出模板文件
 * @date 2020/5/23
 */
@Slf4j
@RestController
public class WhpxxszController {

    /**
     * 下载模板
     * @return 返回excel模板
     */
    @RequestMapping(value = "/downloadWhpModel/{id}", method = RequestMethod.GET, produces ="application/json;charset=UTF-8")
    @ResponseBody
//    @ApiOperation(value = "模板下载", httpMethod = "GET", produces = "application/json;charset=UTF-8")
    public Object downloadModel(@PathVariable("id") String id){
        System.out.println("进来没：："+id);
        //文件上级目录
        String PATH = "static/assets/templates/Excel";
        //文件名
        String FILENAME = null;
        //下载展示的文件名
        String NEWNAME="";
        if(id.equals("1")){
            FILENAME = "henu_exam_fill.xlsx";
            NEWNAME="河南大学在线考试系统填空题模板";
        }else if(id.equals("2")){
            FILENAME = "henu_exam_judge.xlsx";
            NEWNAME="河南大学在线考试系统判断题模板";
        }else if(id.equals("3"))
        {
            FILENAME = "henu_exam_multi.xlsx";
            NEWNAME="河南大学在线考试系统选择题模板";
        }else if(id.equals("4"))
        {
            FILENAME = "henu_stu.xlsx";
            NEWNAME="河南大学在线考试系统学生信息模板";
        }
        else if(id.equals("5"))
        {
            FILENAME = "henu_teacher.xlsx";
            NEWNAME="河南大学在线考试系统教师信息模板";
        }


        ResponseEntity<InputStreamResource> response = null;
        try {
            response = DownloadFileUtil.download(PATH, FILENAME, NEWNAME);
            System.out.println("response：："+response);
        } catch (Exception e) {
            log.error("下载模板失败");
//            e.printStackTrace();
        }
        return response;
    }
}
