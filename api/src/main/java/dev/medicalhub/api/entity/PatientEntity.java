package dev.medicalhub.api.entity;

import dev.medicalhub.api.model.Patient;
import dev.medicalhub.api.model.PatientCountProcedureModel;
import dev.medicalhub.api.model.PatientInfoModel;
import dev.medicalhub.api.model.PatientModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "patient")
public class PatientEntity {

    public PatientEntity(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(length = 16)
    private String passportData;
    @Column(length = 16)
    private String snils;
    @OneToMany
    private List<ReceptionEntity> historyReception;
    @OneToOne(cascade = CascadeType.ALL)
    private MedicalBookEntity medicalBook;
    public PatientEntity(PatientModel patientModel){
        id = patientModel.getId();
        fullName = patientModel.getFullName();
        //passportData = patientModel.getPassportData();
        //snils = patientModel.getSnils();
        medicalBook = new MedicalBookEntity(patientModel.getMedicalBookId());
    }
    public PatientModel toDTOModel(){
        return (PatientModel) new PatientModel()
                .setMedicalBookId(medicalBook.getId())
                .setId(id)
                .setFullName(fullName);
                //.setPassportData(passportData)
                //.setSnils(snils);
    }
    public PatientInfoModel toDTOInfoModel(){
        return (PatientInfoModel) new PatientInfoModel()
                .setMedicalBookId(medicalBook.getId())
                .setPassportData(Integer.parseInt(passportData))
                .setSnils(Integer.parseInt(snils))
                .setId(id)
                .setFullName(fullName);
    }
    public Patient toDTO(){
        return new Patient()
                .setId(id)
                .setFullName(fullName);
                //.setPassportData(passportData)
                //.setSnils(snils);
    }

    public PatientCountProcedureModel toDTOCountProcedureModel(long countProcedure){
        return (PatientCountProcedureModel) new PatientCountProcedureModel()
                .setCount(countProcedure)
                .setId(id)
                .setFullName(fullName);
    }
}
