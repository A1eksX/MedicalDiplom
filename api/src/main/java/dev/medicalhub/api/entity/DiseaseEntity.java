package dev.medicalhub.api.entity;

import dev.medicalhub.api.model.DiseaseModel;
import dev.medicalhub.api.model.DiseaseWithProcedures;
import dev.medicalhub.api.model.DiseaseWithProceduresModel;
import dev.medicalhub.api.model.Procedure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "disease")
public class DiseaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String diagnosis;
    private ZonedDateTime treatmentStart;
    private ZonedDateTime treatmentEnd;
    private String rezeptura;
    @OneToMany
    private List<ProcedureEntity> procedures;
    @ManyToOne
    @JoinColumn(name = "medicalBook_id")
    private MedicalBookEntity medicalBook;

    public void addProcedure(ProcedureEntity procedureEntity){
        if (procedures.isEmpty())
            procedures = new ArrayList<>();
        procedures.add(procedureEntity);
    }

    public DiseaseWithProceduresModel toDTOWithProceduresModel(){
        List<Procedure> procedureModels = procedures.stream().map(ProcedureEntity::toDTO).toList();
        return (DiseaseWithProceduresModel) new DiseaseWithProceduresModel()
                .setMedicalBookId(medicalBook.getId())
                .setProcedures(procedureModels)
                .setId(id)
                .setDiagnosis(diagnosis)
                .setTreatmentStart(treatmentStart)
                .setTreatmentEnd(treatmentEnd)
                .setRezeptura(rezeptura);
    }

    public DiseaseWithProcedures toDTOWithProcedures(){
        List<Procedure> procedureModels = procedures.stream().map(ProcedureEntity::toDTO).toList();
        return (DiseaseWithProcedures) new DiseaseWithProcedures()
                .setProcedures(procedureModels)
                .setId(id)
                .setDiagnosis(diagnosis)
                .setTreatmentStart(treatmentStart)
                .setTreatmentEnd(treatmentEnd)
                .setRezeptura(rezeptura);
    }

    public DiseaseModel toDTOModel() {
        return (DiseaseModel) new DiseaseModel()
                .setMedicalBookId(medicalBook.getId())
                .setTreatmentStart(treatmentStart)
                .setTreatmentEnd(treatmentEnd)
                .setDiagnosis(diagnosis)
                .setRezeptura(rezeptura)
                .setId(id);
    }
}
