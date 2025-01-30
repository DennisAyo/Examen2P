package com.examen.examen2p.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "branches")
public class Branch implements Serializable {
    @Id
    private String id;
    private String emailAddress;
    private String name;
    private String phoneNumber;
    private String state;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private List<BranchHoliday> branchHolidays;

    @Data
    public static class BranchHoliday {
        private LocalDate date;
        private String name;
    }
} 