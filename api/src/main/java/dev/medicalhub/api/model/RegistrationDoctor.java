package dev.medicalhub.api.model;

import dev.medicalhub.api.entity.SpecializationEntity;
import dev.medicalhub.api.repository.SpecializationRepo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class RegistrationDoctor {
    private String fullName;
    private long diplomaNumber;
    private String password;
    private String specialization;
    private long medicalInstitutionId;
}
