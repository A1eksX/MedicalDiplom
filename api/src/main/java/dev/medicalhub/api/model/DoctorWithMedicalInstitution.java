package dev.medicalhub.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DoctorWithMedicalInstitution extends Doctor{
    private MedicalInstitutionModel medicalInstitutionModel;
}
