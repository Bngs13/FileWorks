package com.task.fileworks.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListDto {
    private List<CsvInputDto> csvInputDtoList;
}
