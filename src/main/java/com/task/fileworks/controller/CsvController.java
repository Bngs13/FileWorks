package com.task.fileworks.controller;

import com.task.fileworks.common.constant.PathValues;
import com.task.fileworks.common.exception.FileReadException;
import com.task.fileworks.dto.CsvInputDto;
import com.task.fileworks.dto.ListDto;
import com.task.fileworks.service.CsvInputService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class CsvController extends BaseController {

    @Autowired
    private final CsvInputService csvInputService;

    @PostMapping(path = PathValues.UPLOAD_PATH)
    public ResponseEntity uploadCsvFile(@RequestParam(value = "file") MultipartFile multipart)
            throws FileReadException, IOException {

        csvInputService.uploadDataByCsv(multipart);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping(path = PathValues.GET_ALL_DATA)
    public ResponseEntity<ListDto> getAllData() {

        ListDto inputDtoList = csvInputService.getAllData();

        return ResponseEntity.status(HttpStatus.OK).body(inputDtoList);
    }

    @GetMapping(path = PathValues.GET_DATA_BY_CODE)
    public ResponseEntity<CsvInputDto> getDataByCode(@PathVariable("code") final String code) {
        if (StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }

        CsvInputDto dto = csvInputService.getByCode(code);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping(path = PathValues.DELETE_ALL_DATA)
    public ResponseEntity<?> deleteAllData() {

        csvInputService.deleteAllData();
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
