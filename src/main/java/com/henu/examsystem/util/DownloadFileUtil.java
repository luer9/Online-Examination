package com.henu.examsystem.util;/**
 * 项目名称：exam-system
 * 类 名 称：DownloadFileUtil
 * 类 描 述：TODO
 * 创建时间：2020/5/23 13:44
 * 创 建 人：10265
 */

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
/**
 * @author yll
 * @description 文件下载
 * @date 2020/5/23
 */
public class DownloadFileUtil {
    public static final String separator = File.separator;

    /**
     * 下载样表
     * @param filePath 文件上级目录
     * @param fileName 文件名
     * @param newName  下载的展示文件名
     * @return 响应
     */
    public static ResponseEntity<InputStreamResource> download(String filePath, String fileName, String newName) {
//        String route = "static" + separator + "templates" + separator;
        String path = null;
        ResponseEntity<InputStreamResource> response = null;
//        System.out.println("route:" + route);
        System.out.println("separator "+separator );
        System.out.println(filePath +"  " + fileName);
        path = filePath + separator + fileName;
        System.out.println("path:" + path);
        try {

            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            //File file = new File(path);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition",
                    "attachment; filename="
                            + new String(newName.getBytes("gbk"), "iso8859-1") + ".xlsx");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            response = ResponseEntity.ok().headers(headers)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(inputStream));
        } catch (FileNotFoundException e1) {
            System.out.println("找不到指定的文件");
//            log.error("找不到指定的文件", e1);
        } catch (IOException e) {
            System.out.println("获取不到文件流");
//            log.error("获取不到文件流", e);
        }
        return response;
    }
}

