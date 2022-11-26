package com.task.fileworks.service;

import com.task.fileworks.common.constant.CsvConstants;
import com.task.fileworks.common.exception.FileReadException;
import com.task.fileworks.converter.CsvInputConverter;
import com.task.fileworks.domain.entity.CsvInput;
import com.task.fileworks.domain.repository.CsvInputRepo;
import com.task.fileworks.dto.CsvInputDto;
import com.task.fileworks.dto.ListDto;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class CsvInputServiceImpl implements CsvInputService {

    @Autowired
    private final CsvInputConverter csvInputConverter;

    @Autowired
    private final CsvInputRepo csvInputRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvInputServiceImpl.class);

    @Override
    public void uploadDataByCsv(MultipartFile multipartFile) throws FileReadException, IOException {
        List<CsvInput> csvInputList = new ArrayList<>();
        InputStreamReader isReader = new InputStreamReader(multipartFile.getInputStream(), StandardCharsets.UTF_8);
        try (BufferedReader fileReader = new BufferedReader(isReader)) {
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.Builder.create()
                    .setHeader("source", "codeListCode", "code", "displayValue", "longDescription", "fromDate", "toDate", "sortingPriority")
                    .setAllowMissingColumnNames(true)
                    .setSkipHeaderRecord(true)
                    .build());
            List<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord row : csvRecords) {
                String fromDate = row.get("fromDate");
                String toDate = row.get("toDate");
                CsvInput csvInput = CsvInput.builder()
                        .source(row.get("source"))
                        .codeListCode(row.get("codeListCode"))
                        .code(row.get("code"))
                        .displayValue(row.get("displayValue"))
                        .longDescription(row.get("longDescription"))
                        .fromDate(StringUtils.isEmpty(fromDate) ? null : LocalDate.parse(fromDate, CsvConstants.DATE_PATTERN))
                        .toDate(StringUtils.isEmpty(toDate) ? null : LocalDate.parse(toDate, CsvConstants.DATE_PATTERN))
                        .sortingPriority(row.get(7))
                        .build();

                csvInputList.add(csvInput);
            }
        } catch (IOException e) {
            LOGGER.error("Error while csv casting to object, e: {}", e.getMessage());
            throw new FileReadException("Exception while reading file");
        } finally {
            isReader.close();
        }
        csvInputRepo.saveAll(csvInputList);
    }

    @Override
    public ListDto getAllData() {
        return csvInputConverter.convertListDto(csvInputRepo.findAll());
    }

    @Override
    public CsvInputDto getByCode(String code) {
        return csvInputConverter.convertDto(csvInputRepo.findByCode(code));
    }

    @Override
    public void deleteAllData() {
        csvInputRepo.deleteAll();
    }
}
