package dev.medicalhub.api.entity;

import dev.medicalhub.api.model.Doctor;
import dev.medicalhub.api.model.DoctorModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "doctor")
public class DoctorEntity {

    @Id
    private long diplomaNumber;
    private String fullName;
    private String hashCodePassword;
    private String password;
    private String accessDuringWorkingPeriod;

    @ManyToOne
    private SpecializationEntity specialization;
    @ManyToOne
    private MedicalInstitutionEntity medicalInstitution;

    @OneToMany
    private List<ReceptionEntity> historyReception = new ArrayList<>();
     public void addReception(ReceptionEntity reception){
         historyReception.add(reception);
     }

    public DoctorModel toDTOModel(){
        return (DoctorModel) new DoctorModel()
                .setMedicalInstitution_id(medicalInstitution.getId())
                .setAccessDusringWorkingPeriod(accessDuringWorkingPeriod)
                .setSpecialization(specialization.getName())
                .setFullName(fullName)
                .setId(diplomaNumber);
    }
    public Doctor toDTO(){
        return new Doctor()
                .setAccessDusringWorkingPeriod(accessDuringWorkingPeriod)
                .setSpecialization(specialization.getName())
                .setFullName(fullName)
                .setId(getDiplomaNumber());
    }
}
