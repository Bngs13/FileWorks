package com.task.fileworks.service;

import com.task.fileworks.common.exception.FileReadException;
import com.task.fileworks.domain.entity.CsvInput;
import com.task.fileworks.domain.repository.CsvInputRepo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CsvInputServiceImplIT {

    @Autowired
    private CsvInputRepo csvInputRepo;

    @Autowired
    private CsvInputServiceImpl csvInputService;

    @Nested
    class UploadDataByCsv {

        @Test
        //@Order(1)
        public void should_throw_file_read_exception_when_invalid_header_csv_is_uploaded()
                throws IOException {

            //Given
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("invalid_header_data.csv").getFile());
            byte[] byteArray = Files.readAllBytes(Paths.get(file.getPath()));
            MultipartFile multipartFile = new
                    MockMultipartFile("data", "invalid_header_data.csv",
                    "text/csv", byteArray);

            boolean dynamicFail = true;
            try {
                csvInputService.uploadDataByCsv(multipartFile);
            } catch (FileReadException e) {
                dynamicFail = false;
                List<CsvInput> csvInputList = csvInputRepo.findAll();
                assertNotNull(csvInputList);
                assertEquals(0, csvInputList.size());
            }
            if (dynamicFail) {
                fail();
            }
        }


        @Test
        //@Order(2)
        public void should_throw_nothing_when_valid_csv_is_uploaded() throws IOException, FileReadException {

            //Given
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("valid_data.csv").getFile());
            byte[] byteArray = Files.readAllBytes(Paths.get(file.getPath()));
            MultipartFile multipartFile = new
                    MockMultipartFile("data", "valid_data.csv",
                    "text/csv", byteArray);

            //When
            csvInputService.uploadDataByCsv(multipartFile);

            //Then
            List<CsvInput> csvInputList = csvInputRepo.findAll();
            assertNotNull(csvInputList);
            assertNotEquals(0, csvInputList.size());
        }
    }


    @AfterAll
    void afterAll() {
        csvInputRepo.deleteAll();
    }

}