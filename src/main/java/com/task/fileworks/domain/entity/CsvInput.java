package com.task.fileworks.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@Table(name = "csv_input")
public class CsvInput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Long id;

    @Column
    private String source;

    @Column
    private String codeListCode;

    @Column(unique = true)
    private String code;

    @Column
    private String displayValue;

    @Column
    private String longDescription;

    @Column
    private LocalDate fromDate;

    @Column
    private LocalDate toDate;

    @Column
    private String sortingPriority;

}
