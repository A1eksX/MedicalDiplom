package dev.medicalhub.api.model;

import dev.medicalhub.api.entity.MedicalInstitutionEntity;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
public class DoctorModel extends Doctor {
    private long medicalInstitution_id;
}
