package com.task.fileworks.domain.repository;

import com.task.fileworks.domain.entity.CsvInput;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CsvInputRepo extends PagingAndSortingRepository<CsvInput, Long>, JpaSpecificationExecutor<CsvInput> {

    List<CsvInput> findAll();

    CsvInput findByCode(String code);

}
