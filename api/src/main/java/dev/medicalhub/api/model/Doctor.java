package dev.medicalhub.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Doctor {
    private long id;
    private String fullName;
    private String accessDusringWorkingPeriod;
    private String specialization;
}