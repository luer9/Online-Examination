package com.henu.examsystem.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportExcel {
    boolean batchImport(String fileName, MultipartFile file) throws Exception;
}
