package com.task.fileworks.converter;

import com.task.fileworks.domain.entity.CsvInput;
import com.task.fileworks.dto.CsvInputDto;
import com.task.fileworks.dto.ListDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvInputConverter {

    public ListDto convertListDto(List<CsvInput> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        ListDto listDto = new ListDto();
        listDto.setCsvInputDtoList(entityList.stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList()));
        return listDto;
    }

    public CsvInputDto convertDto(CsvInput entity) {
        if (entity == null) {
            return null;
        }
        CsvInputDto dto = new CsvInputDto();
        dto.setCode(entity.getCode());
        dto.setSource(entity.getSource());
        dto.setCodeListCode(entity.getCodeListCode());
        dto.setDisplayValue(entity.getDisplayValue());
        dto.setLongDescription(entity.getLongDescription());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        dto.setSortingPriority(entity.getSortingPriority());

        return dto;
    }
}
