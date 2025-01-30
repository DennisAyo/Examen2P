package com.examen.examen2p.mapper;

import com.examen.examen2p.dto.BranchRequestDTO;
import com.examen.examen2p.dto.BranchResponseDTO;
import com.examen.examen2p.dto.BranchHolidayDTO;
import com.examen.examen2p.model.Branch;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.util.ArrayList;

@Component
public class BranchMapper implements Serializable{
    
    public Branch toEntity(BranchRequestDTO dto) {
        Branch branch = new Branch();
        branch.setEmailAddress(dto.getEmailAddress());
        branch.setName(dto.getName());
        branch.setPhoneNumber(dto.getPhoneNumber());
        branch.setState(dto.getState());
        branch.setBranchHolidays(new ArrayList<>());
        return branch;
    }

    public BranchResponseDTO toDTO(Branch entity) {
        BranchResponseDTO dto = new BranchResponseDTO();
        dto.setId(entity.getId());
        dto.setEmailAddress(entity.getEmailAddress());
        dto.setName(entity.getName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setState(entity.getState());
        dto.setCreationDate(entity.getCreationDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
        
        if (entity.getBranchHolidays() != null) {
            dto.setBranchHolidays(entity.getBranchHolidays().stream()
                .map(this::toHolidayDTO)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public Branch.BranchHoliday toHolidayEntity(BranchHolidayDTO dto) {
        Branch.BranchHoliday holiday = new Branch.BranchHoliday();
        holiday.setDate(dto.getDate());
        holiday.setName(dto.getName());
        return holiday;
    }

    public BranchHolidayDTO toHolidayDTO(Branch.BranchHoliday entity) {
        BranchHolidayDTO dto = new BranchHolidayDTO();
        dto.setDate(entity.getDate());
        dto.setName(entity.getName());
        return dto;
    }
} 