package dev.medicalhub.api.entity;

import dev.medicalhub.api.model.DiseaseWithProcedures;
import dev.medicalhub.api.model.DiseaseWithProceduresModel;
import dev.medicalhub.api.model.MedicalBookModel;
import dev.medicalhub.api.model.MedicalBookWithDiseaseModel;
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
@Table(name = "medicalBook")
public class MedicalBookEntity {

    public MedicalBookEntity(Long id) {
        this.id = id;
    }

    public MedicalBookEntity(MedicalBookModel medicalBookModel){
        id =medicalBookModel.getId();
        patient =new PatientEntity(medicalBookModel.getId());
        allergy = medicalBookModel.getAllergy();
        graftCertificate =medicalBookModel.getGraftCertificate();
        groupBlood = medicalBookModel.getGroupBlood();
        RhFactor = medicalBookModel.isRhFactor();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private PatientEntity patient;
    @OneToMany
    private List<DiseaseEntity> historyDisease;
    private String allergy;
    private String graftCertificate;
    private int groupBlood;
    private boolean RhFactor;

    public void addHistoryDisease(DiseaseEntity disease)
    {
        if (historyDisease.isEmpty())
            historyDisease = new ArrayList<>();
        historyDisease.add(disease);
    }

    public MedicalBookModel toDTOModel(){
        return (MedicalBookModel) new MedicalBookModel()
                .setPatientId(patient.getId())
                .setId(id)
                .setAllergy(allergy)
                .setGraftCertificate(graftCertificate)
                .setGroupBlood(groupBlood)
                .setRhFactor(RhFactor);
    }

    public MedicalBookWithDiseaseModel toDTOWithDiseaseModel(){
        List<DiseaseWithProcedures> diseaseEntities = historyDisease.stream().map(DiseaseEntity::toDTOWithProcedures).toList();
        return (MedicalBookWithDiseaseModel) new MedicalBookWithDiseaseModel()
                .setPatientId(patient.getId())
                .setHistoryDisease(diseaseEntities)
                .setId(id)
                .setAllergy(allergy)
                .setGraftCertificate(graftCertificate)
                .setGroupBlood(groupBlood)
                .setRhFactor(isRhFactor());
    }

}
