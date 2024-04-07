package dev.medicalhub.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class RegistrationPatientModel {
    private Long patientId;
    private Long medicalBookId;
    private String fullName;
    private int passportData;
    private int snils;
    private String allergy;
    private String graftCertificate;
    private int groupBlood;
    private boolean rhFactor;
}
