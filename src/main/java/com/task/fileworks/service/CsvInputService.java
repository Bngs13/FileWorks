package com.task.fileworks.service;

import com.task.fileworks.common.exception.FileReadException;
import com.task.fileworks.dto.CsvInputDto;
import com.task.fileworks.dto.ListDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CsvInputService {

    void uploadDataByCsv(MultipartFile multipartFile) throws FileReadException, IOException;

    ListDto getAllData();

    CsvInputDto getByCode(String code);

    void deleteAllData();
}
